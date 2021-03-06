package project.se.information.book;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.cengalabs.flatui.views.FlatTextView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import project.se.model.Book_Detail;
import project.se.rest.ApiService;
import project.se.talktodeaf.R;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class BookDetail extends ActionBarActivity implements ObservableScrollViewCallbacks {
    FlatTextView bookName,bookDes,bookPage,bookAuthor,bookPublisher,bookPrice,bookTitle;
    ImageView bookImage;
    String BookName,BookDes,BookPage,BookAuthor,BookPublisher,BookPrice,BookImage;
    String book_name = BookInfo.getBook_name();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ObservableScrollView scrollView = (ObservableScrollView) findViewById(R.id.scroll);
        scrollView.setScrollViewCallbacks(this);
        bookImage = (ImageView) findViewById(R.id.imageView);
        bookName = (FlatTextView) findViewById(R.id.book_name);
        bookDes = (FlatTextView) findViewById(R.id.book_des);
        bookPage = (FlatTextView) findViewById(R.id.book_page);
        bookAuthor = (FlatTextView) findViewById(R.id.book_author);
        bookPublisher = (FlatTextView) findViewById(R.id.book_publisher);
        bookPrice = (FlatTextView) findViewById(R.id.book_price);
        bookTitle = (FlatTextView) findViewById(R.id.book_title);
        GsonBuilder builder = new GsonBuilder();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://talktodeafphp-talktodeaf.rhcloud.com")
                .setConverter(new GsonConverter(builder.create()))
                .build();
        ApiService retrofit = restAdapter.create(ApiService.class);
        retrofit.getBookDetailByNameWithCallback(book_name, new Callback<Book_Detail>() {

            @Override
            public void success(Book_Detail listBook, Response response) {
                Book_Detail book = listBook;


                BookName = book.getBook_name();
                BookDes = book.getBook_description();
                BookPage = book.getBook_page_number();
                BookAuthor = book.getBook_author();
                BookPublisher = book.getBook_publisher();
                BookPrice = book.getBook_price();
                BookImage = book.getBook_image();

                bookTitle.setText("" + BookName);
                bookName.setText("ชื่อหนังสือ: " + BookName);
                bookDes.setText("รายละเอียด: " + BookDes);
                bookPage.setText("จำนวนหน้า: " + BookPage + " หน้า");
                bookAuthor.setText("ชื่อผู้แต่ง: " + BookAuthor);
                bookPublisher.setText("สำนักพิมพ์: " + BookPublisher);
                bookPrice.setText("ราคา: " + BookPrice + " บาท");
                Picasso.with(BookDetail.this).load("http://talktodeafphp-talktodeaf.rhcloud.com" + BookImage).into(bookImage);

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(BookDetail.this, "Connection fail please try again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onScrollChanged(int i, boolean b, boolean b2) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        ActionBar ab = getSupportActionBar();
        if (scrollState == ScrollState.UP) {
            if (ab.isShowing()) {
                ab.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!ab.isShowing()) {
                ab.show();
            }
        }
    }
}

