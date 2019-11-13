package com.example.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView textResult;
    String URL="https://jsonplaceholder.typicode.com/";
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textResult=findViewById(R.id.text_view_result);

      //  Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
       // getPosts();
        //getComments();
        //createPost();
        UpdatePost();
        //deletePost();
    }

    private void deletePost() {
        Call<Void> call = jsonPlaceHolderApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textResult.setText("code: "+response.code());

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textResult.setText(t.getMessage());

            }
        });

    }

    //--------
    private void UpdatePost() {
        Post post = new Post(12,null,"New Text");
       Call<Post> call = jsonPlaceHolderApi.putPost(5,post);
        //Call<Post> call = jsonPlaceHolderApi.patchPost(5,post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful())
                {
                    textResult.setText("code: "+response.code());
                    return;
                }
                Post postResponse = response.body();
                String content ="";
                content += "code: " + response.code()+ "\n";
                content += "Id: " +postResponse.getId()+ "\n";
                content += "User Id: " +postResponse.getUserId()+ "\n";
                content += "Title: " +postResponse.getTitle()+ "\n";
                content += "Text: " +postResponse.getText()+ "\n\n";
                textResult.setText(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textResult.setText(t.getMessage());

            }
        });
    }
//----
    private void createPost()
    {
        //Post post = new Post(23,"New Title","New Text");
        Map<String,String> fields = new HashMap<>();
        fields.put("userId","25");
        fields.put("title","New Title");
        Call<Post> call = jsonPlaceHolderApi.createPost(fields);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful())
                {
                    textResult.setText("code: "+response.code());
                    return;
                }
                Post postResponse = response.body();
                String content ="";
                content += "code: " + response.code()+ "\n";
                content += "Id: " +postResponse.getId()+ "\n";
                content += "User Id: " +postResponse.getUserId()+ "\n";
                content += "Title: " +postResponse.getTitle()+ "\n";
                content += "Text: " +postResponse.getText()+ "\n\n";
                textResult.setText(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textResult.setText(t.getMessage());

            }
        });
    }

    //------------
    private void getComments()
    {
        Call<List<Comments>> call = jsonPlaceHolderApi.getComments(3);
        call.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {

                if(!response.isSuccessful())
                {
                    textResult.setText("code: "+response.code());
                    return;
                }
                List<Comments> comments = response.body();
                for(Comments comment : comments)
                {
                    String content ="";
                    content += "Id: " +comment.getId()+ "\n";
                    content += "Post Id: " +comment.getPostId()+ "\n";
                    content += "Name: " +comment.getName()+ "\n";
                    content += "Email: " +comment.getEmail()+ "\n";
                    content += "Text: " +comment.getText()+ "\n\n";
                    textResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    //-----------
    private void getPosts()
    {

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful())
                {
                    textResult.setText("code: "+response.code());
                    return;
                }
                List<Post> posts = response.body();
                for(Post post1 : posts)
                {
                    String content ="";
                    content += "Id: " +post1.getId()+ "\n";
                    content += "User Id: " +post1.getUserId()+ "\n";
                    content += "Title: " +post1.getTitle()+ "\n";
                    content += "Text: " +post1.getText()+ "\n\n";
                    textResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }
    //--
}
