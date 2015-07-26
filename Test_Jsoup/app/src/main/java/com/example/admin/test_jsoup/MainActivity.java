package com.example.admin.test_jsoup;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;


public class MainActivity extends ActionBarActivity {
    TextView textResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textResult = (TextView) findViewById(R.id.textview1);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {

                    String loginURL = "http://appz.vu.ac.th/vufamily2/login.aspx";

                    Connection.Response response = Jsoup.connect(loginURL)
                            .method(Connection.Method.GET)
                            .execute();

                    Document loginPage = response.parse();
                    String  VIEWSTATE = loginPage.getElementById("__VIEWSTATE").val();

                    Document doc = Jsoup.connect(loginURL)
                            .data("__VIEWSTATE", loginPage.getElementById("__VIEWSTATE").val())
                            .data("__VIEWSTATEGENERATOR","95D3CD3A")
                            .data("__EVENTVALIDATION",loginPage.getElementById("__EVENTVALIDATION").val())
                            .data("ctl00$HeaderContentPlaceHolder$defaultlogin$UserName", "username")
                            .data("ctl00$HeaderContentPlaceHolder$defaultlogin$Password", "password")
                            .data("ctl00$HeaderContentPlaceHolder$defaultlogin$LoginButton", "0")
                            .data("ctl00$CP1$LoginView1$Login1$LoginButton", "เข้าสู่ระบบ")
                            .cookies(response.cookies())
                            .referrer("http://appz.vu.ac.th/vufamily2/Default.aspx?tabindex=0#")
                            .data("SheetContentPlaceHolder_TabContainer1_ClientState", "{&quot;ActiveTabIndex&quot;:1,&quot;TabEnabledState&quot;:[true,true,true,true,true,true],&quot;TabWasLoadedOnceState&quot;:[false,false,false,false,false,false]}")
                            .post();

                             Document doc2 = Jsoup
                            .connect("http://appz.vu.ac.th/vufamily2/Default.aspx?tabindex=0#")
                            .data("SheetContentPlaceHolder_TabContainer1_ClientState","{&quot;ActiveTabIndex&quot;:1,&quot;TabEnabledState&quot;:[false,false,false,false,false,false],&quot;TabWasLoadedOnceState&quot;:[false,false,false,false,false,false]}")
                            .cookies(response.cookies())
                                     .referrer("http://appz.vu.ac.th/vufamily2/Default.aspx?tabindex=0#")
                                     .post();
                    //------
                    Element table = doc2.getElementById("SheetContentPlaceHolder_TabContainer1_TabPanel1_DataList1"); //select the first table.

                    Log.e("aa", String.valueOf(table));
                    Log.e("dd", String.valueOf(doc.outerHtml()));
                    String title = doc.title();
                    return title;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String string) {
                super.onPostExecute(string);
                textResult.setText(string);
                Toast.makeText(getApplication(),Html.fromHtml(string),Toast.LENGTH_SHORT).show();
            }
        }.execute();




    }


}
