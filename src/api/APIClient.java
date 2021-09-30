package api;

import object.*;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class APIClient {
    private String url = "http://localhost:6789/";
    public static APIClient getInstance(){
        return new APIClient();
    }
    private APIClient(){

    }

    public List<Exam> getExam() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://localhost:6789/exam/");
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(resultString);
        JSONArray result = new JSONArray(resultString);
        ArrayList<ExamInfo> ExamInfoList = new ArrayList<>();
        for(int i = 0;i<result.length();i++){
            String id = result.getJSONObject(i).getString("id");
            String description = result.getJSONObject(i).getString("description");
            String name = result.getJSONObject(i).getString("name");
            ExamInfo ExamInfo = new ExamInfo(id,name,description);
            ExamInfoList.add(ExamInfo);
        }
        List<Bank> BankList = new ArrayList<>();
        ArrayList<Exam> ExamList = new ArrayList<>();
        for(int i = 0;i<ExamInfoList.size();i++){
            HttpGet httpGetExam = new HttpGet("http://localhost:6789/exam/"+ExamInfoList.get(i).getId());
            httpResponse = httpClient.execute(httpGetExam);
            resultString = EntityUtils.toString(httpResponse.getEntity());
            JSONObject resultJSON = new JSONObject(resultString);
            JSONArray questionArray = resultJSON.getJSONArray("questionList");
            List<Question> questionList = new ArrayList<>();
            for(int j = 0;j<questionArray.length();j++){
                JSONObject questionObject = questionArray.getJSONObject(j);
                System.out.println(questionObject);
                JSONArray questionCorrect = questionObject.getJSONArray("questionCorrect");
                JSONArray answer = questionObject.getJSONArray("answer");

                Question question = new Question(questionObject.getString("question"),
                        JSONtoList(questionCorrect),
                        JSONtoList(answer));
                questionList.add(question);
            }
            Exam exam = new Exam(ExamInfoList.get(i),questionList);
            ExamList.add(exam);
        }
        httpClient.close();
        return ExamList;
    }

    public List<String> JSONtoList(JSONArray jsonArray){
        List<String> list = new ArrayList<>();
        for(int i = 0;i<jsonArray.length();i++){
            list.add(jsonArray.getString(i));
        }
        return list;
    }

    public List<Bank> getBank() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://localhost:6789/bank/");
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String resultString = EntityUtils.toString(httpResponse.getEntity());
        JSONArray resultArray = new JSONArray(resultString);
        ArrayList<BankInfo> BankInfoList = new ArrayList<>();
        for(int i = 0;i<resultArray.length();i++){
            String id = resultArray.getJSONObject(i).getString("id");
            String description = resultArray.getJSONObject(i).getString("description");
            String name = resultArray.getJSONObject(i).getString("name");
            BankInfo bankInfo = new BankInfo(id,name,description);
            BankInfoList.add(bankInfo);
        }
        List<Bank> BankList = new ArrayList<>();

        for(int i = 0;i<BankInfoList.size();i++){
            HttpGet httpGetExam = new HttpGet("http://localhost:6789/bank/"+BankInfoList.get(i).getId());
            httpResponse = httpClient.execute(httpGetExam);
            resultString = EntityUtils.toString(httpResponse.getEntity());
            JSONObject resultJSON = new JSONObject(resultString);
            JSONArray questionArray = resultJSON.getJSONArray("questionList");
            List<Question> questionList = new ArrayList<>();
            for(int j = 0;j<questionArray.length();j++){
                JSONObject questionObject = questionArray.getJSONObject(j);
                System.out.println(questionObject);
                JSONArray questionCorrect = questionObject.getJSONArray("questionCorrect");
                JSONArray answer = questionObject.getJSONArray("answer");

                Question question = new Question(questionObject.getString("question"),
                        JSONtoList(questionCorrect),
                        JSONtoList(answer));
                questionList.add(question);
            }
            Bank bank = new Bank(BankInfoList.get(i),questionList);
            BankList.add(bank);
        }
        httpClient.close();
        return BankList;
    }

    public HttpResponse login(String username,String password) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url+"api/auth/signin");
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        JSONObject loginRequest = new JSONObject();
        loginRequest.put("username",username);
        loginRequest.put("password",password);
        httpPost.setEntity(new StringEntity(loginRequest.toString()));
        HttpResponse response = httpClient.execute(httpPost);
        return response;

        /*if(response.getStatusLine().getStatusCode()==200){
            JSONObject accountResponse = new JSONObject(EntityUtils.toString(response.getEntity()));
            Account account = Account.getInstance();
            JSONArray roleArray =  accountResponse.getJSONArray("roles");
            List<String> roleList= new ArrayList<>();
            for(int i = 0;i<roleArray.length();i++){
                roleList.add(roleArray.getString(i));
            }
            if(roleList.contains("ROLE_ADMIN")||roleList.contains("ROLE_MODERATOR")){
                String accessToken = accountResponse.getString("accessToken");
                String Id = accountResponse.getString("id");
                account.setId(accessToken);
                account.setId(Id);
            }
        }*/
    }
    public void addExam(Exam exam) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url+"exam/add");
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        httpPost.setHeader(HttpHeaders.AUTHORIZATION,"Bearer "+Account.getInstance().getAccessToken());
        httpPost.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println(exam.toJSON());
        httpPost.setEntity(new StringEntity(exam.toJSON().toString(),"UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        System.out.println(Account.getInstance().getAccessToken());
        System.out.println(response.toString()  );
    }
    public void deleteExam(Exam exam) throws IOException{
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url+"exam/remove");
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        httpPost.setHeader(HttpHeaders.AUTHORIZATION,"Bearer "+Account.getInstance().getAccessToken());
        httpPost.addHeader("Access-Control-Allow-Origin", "*");
        httpPost.setEntity(new StringEntity(exam.toJSON().toString(),"UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        System.out.println(exam.toJSON().toString());
        System.out.println(response.toString()  );
    }
    public void addBank(Bank bank) throws IOException{
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url+"bank/add");
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        httpPost.setHeader(HttpHeaders.AUTHORIZATION,"Bearer "+Account.getInstance().getAccessToken());
        httpPost.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println(bank.toJSON());
        httpPost.setEntity(new StringEntity(bank.toJSON().toString(),"UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        System.out.println(Account.getInstance().getAccessToken());
        System.out.println(response.toString());
    }
    public void deleteBank(Bank bank) throws IOException{
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url+"bank/remove");
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        httpPost.setHeader(HttpHeaders.AUTHORIZATION,"Bearer "+Account.getInstance().getAccessToken());
        httpPost.addHeader("Access-Control-Allow-Origin", "*");
        httpPost.setEntity(new StringEntity(bank.toJSON().toString(),"UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        System.out.println(bank.toJSON().toString());
        System.out.println(response.toString()  );
    }
}
