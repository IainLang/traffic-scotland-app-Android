package com.example.mpd_coursework_iainlang;
//Iain Lang - s1822179

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.shapes.Shape;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FetchRss extends AppCompatActivity {

URL url;

    URL urlSource1 = new URL("https://trafficscotland.org/rss/feeds/currentincidents.aspx");
    URL urlSource2 = new URL("https://trafficscotland.org/rss/feeds/roadworks.aspx");
    URL urlSource3 = new URL("https://trafficscotland.org/rss/feeds/plannedroadworks.aspx");

    Button HomeBtn;
    ListView RssLV;
    ArrayList<String> OutputList;

    ArrayList<String> titles;
    ArrayList<String> descriptions;
    ArrayList<String> links;
    ArrayList<String> geor;
    ArrayList<String> pubdate;
    ArrayList<String> filter;

    SearchView searchView;
    public FetchRss() throws MalformedURLException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fetch_rss);


        searchView = (SearchView) findViewById(R.id.searchBar);

        HomeBtn = (Button)findViewById(R.id.HomeBtn);
        RssLV = (ListView) findViewById(R.id.RssLV);
        OutputList = new ArrayList<String>();

        titles = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        links = new ArrayList<String>();
        geor = new ArrayList<String>();
        pubdate = new ArrayList<String>();

        filter = new ArrayList<String>();



        //search close

        HomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        HomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        RssLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent theIntent;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String title = titles.get(i);
                String description = descriptions.get(i);
                String geo = geor.get(i);
                String pub = pubdate.get(i);
                String link = links.get(i);


//                Uri uri = Uri.parse(links.get(i));
                theIntent = new Intent(getApplicationContext(), DetailedListView.class);
                theIntent.putExtra("title",title);
                theIntent.putExtra("description",description);
                theIntent.putExtra("geo",geo);
                theIntent.putExtra("pub",pub);
                theIntent.putExtra("link",link);


                theIntent.putExtra("position", i);
                Log.e(TAG, "Entry chosen: " + title);

                startActivity(theIntent);
            }
        });


        if ( MainActivity.isCurrentIncidents== true)
        {
            getSupportActionBar().setTitle("Current Incidents");
            url = urlSource1;
        }
        else if (MainActivity.isRoadworks == true)
        {
            getSupportActionBar().setTitle("Current Roadworks");
            url = urlSource2;
        }
        else if (MainActivity.isPlannedRoadworks == true)
        {
            getSupportActionBar().setTitle("Planned Roadworks");
            url = urlSource3;
        }


        new ProcessInBackground().execute();


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    for (int index = 0; index < titles.size(); index++){
                        String currentword = titles.get(index);

                        if (currentword.equals(newText)) {

                                OutputList.add(currentword);
                            }

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(FetchRss.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, OutputList);

                    RssLV.setAdapter(adapter);

                    Log.e(TAG, "Entry chosen: " + newText);

                    Log.e(TAG, "Entry chosen: " + OutputList.size());

//                    Object resource;
//                    fet adapter = new Adapter(getApplicationContext(), resource:0, filteredShapes);
//                    RssLV.setAdapter(adapter);
//                }
                    return false;
                }
            });
        }






//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu., menu);
//
//        final android.widget.SearchView searchView = (android.widget.SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.searchIcon));
//        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                listViewAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return true;
//    }






    public InputStream getInputStream(URL url) {
        Log.e(TAG, "getting input stream: " );

        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }



    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {
        ProgressDialog progressDialog = new ProgressDialog(FetchRss.this);
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading Chosen RSS Feed");
            progressDialog.show();
            Log.e(TAG, "onPreexecute: " );


        }

        @Override
        public Exception doInBackground(Integer... String) {
            Log.e(TAG, "doInbackground: " );

            try {

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                factory.setNamespaceAware(false);
                //Creating new Instance for pull parser
                XmlPullParser xpp = factory.newPullParser();
                //Passing in URL to input Stream and setting Correct Encoding
                xpp.setInput(getInputStream(url), "iso-8859-1");

                boolean insideItem = false;

                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;

                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem) {
                                titles.add(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("description")) {
                            if (insideItem) {
                                descriptions.add(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem) {
                                links.add(xpp.nextText());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("georss:point")) {
                            if (insideItem) {
                                geor.add(xpp.nextText());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                            if (insideItem) {
                                pubdate.add(xpp.nextText());
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                    }
                    eventType = xpp.next();
                }

            } catch (MalformedURLException e) {
                exception = e;
            } catch (XmlPullParserException e) {
                exception = e;
            } catch (IOException e) {
                exception = e;
            }


            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(FetchRss.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, titles);

            RssLV.setAdapter(adapter);
            Log.e(TAG, "onPost: " );

            progressDialog.dismiss();
        }
    }
}