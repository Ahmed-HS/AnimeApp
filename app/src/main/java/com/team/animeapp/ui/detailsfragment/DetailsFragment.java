package com.team.animeapp.ui.detailsfragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.team.animeapp.R;
import com.team.animeapp.data.models.network.Anime;
import com.team.animeapp.data.sources.local.AnimeDatabase;
import com.team.animeapp.data.sources.remote.JikanApi;
import com.team.animeapp.databinding.FragmentDetailsBinding;
import com.team.animeapp.databinding.FragmentSearchBinding;
import com.team.animeapp.domain.Result;
import com.team.animeapp.domain.repositories.AnimeRepository;
import com.team.animeapp.ui.searchfragment.SearchViewModel;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class DetailsFragment extends Fragment {

  FragmentDetailsBinding binding;
  Anime anime;
  @Inject
  JikanApi jikanApi;
  @Inject
  AnimeDatabase animeDatabase;
  int counter;
  CountDownTimer countDownTimer;
  HashMap<String,Integer>daysMap;

    public DetailsFragment() {
        // Required empty public constructor
        super(R.layout.fragment_details);
        CountDownTimer countDownTimer = null;
        daysMap =  new HashMap<>();
        daysMap.put("Saturdays",6);
        daysMap.put("Sundays",7);
        daysMap.put("Mondays",1);
        daysMap.put("Tuesdays",2);
        daysMap.put("Wednesdays",3);
        daysMap.put("Thursdays",4);
        daysMap.put("Fridays",5);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        countDownTimer.cancel();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.bind(view);
        counter = 0;
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            int  currAnimeID = bundle.getInt("anime");

            jikanApi.getAnimeById(currAnimeID).enqueue(new Callback<Anime>() {
                @Override
                public void onResponse(Call<Anime> call, Response<Anime> response) {
                    if(response!=null) {
                        anime = response.body();
                        System.out.println("DataAnime"+anime);
                        binding.movieImg.post(() -> {

                            if(animeDatabase.isInWatchLater(anime)){
                                binding.watchLater.setText("Remove");
                            }else{
                                binding.watchLater.setText("Add");
                            }

                            Glide.with(binding.movieImg)
                                    .load(anime.imageUrl)
                                    .into(binding.movieImg);

                            binding.movieName.setText(anime.title);

                            WebSettings webSettings = binding.webview.getSettings();
                            webSettings.setJavaScriptEnabled(true);
                            webSettings.getCacheMode();
                            binding.webview.loadUrl(anime.trailerUrl);

                            binding.tag.setText(anime.type);

                            binding.simpleRatingBar.setRating(anime.rating%5);

                            binding.movieDesc.setText(anime.synopsis);

                            setCountDown(getDaysLeft(anime.broadcastTime),anime.isAiring);

                            binding.watchLater.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(animeDatabase.isInWatchLater(anime)){
                                        animeDatabase.removeFromWatchLater(anime);
                                        binding.watchLater.setText("Add");
                                    }else{
                                        animeDatabase.addToWatchLater(anime);
                                        binding.watchLater.setText("Remove");
                                    }
                                }
                            });
                        });

                    }
                }
                @Override
                public void onFailure(Call<Anime> call, Throwable t) {
                     Toast.makeText(getContext(),"Please Check Your Network!",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private int getDaysLeft(String bradCastDate) {
        int timeLeft = 0;
        Date currentTime = Calendar.getInstance().getTime();

        if (bradCastDate == null) return -1;
        String[] broadCastDay = anime.broadcastTime.split(" ");

        //GET DAY
        int today = currentTime.getDay();
        int broadDay = daysMap.get(broadCastDay[0]);

        if (broadDay - today < 0)
            timeLeft = broadDay - today + 7;
        else
            timeLeft = broadDay - today;

        return timeLeft;

    }

private void setCountDown(int timeLeft,boolean isAiring) {

    if(timeLeft==-1 || isAiring ==false){
        binding.timer.setVisibility(View.GONE);
        binding.timerHeader.setVisibility(View.GONE);
    }
    countDownTimer = new CountDownTimer(timeLeft*86400*1000, 1000) {

        public void onTick(long millisUntilFinished) {

            // Used for formatting digit to be in 2 digits only

            NumberFormat f = new DecimalFormat("00");
            long days = (millisUntilFinished/3600000)/24;
            long hour = (millisUntilFinished / 3600000) % 24;

            long min = (millisUntilFinished / 60000) % 60;

            long sec = (millisUntilFinished / 1000) % 60;

          //  System.out.println(f.format(days)+":"+ f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));

            binding.timer.setText(f.format(days)+":"+f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));

        }

        @Override
        public void onFinish() {
            binding.timer.setText("00:00:00:00");
        }
    }.start();
}






   /*** private Date convertTODate(String inputString) throws ParseException {
        // Convert input string into a date
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[]arr =  inputString.split("T");
        Date date = inputFormat.parse(arr[0]+" "+arr[1]);
        return  date;
    }

    private void findDifference(Date d1, Date d2)  {
        // Calucalte time difference
        // in milliseconds
        long difference_In_Time = d2.getTime() - d1.getTime();

        // Calucalte time difference in
        // seconds, minutes, hours, years,
        // and days
        long difference_In_Seconds = (difference_In_Time / 1000) % 60;

        long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;

        long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;

        long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));

        long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;


        System.out.print(
                "Difference "
                        + "between two dates is: ");

        System.out.println(
                difference_In_Years
                        + " years, "
                        + difference_In_Days
                        + " days, "
                        + difference_In_Hours
                        + " hours, "
                        + difference_In_Minutes
                        + " minutes, "
                        + difference_In_Seconds
                        + " seconds");
        return ;
    }

    ***/
}