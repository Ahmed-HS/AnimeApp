package com.team.animeapp.domain;

public class Result<t> {
    public static class Success<Data> extends Result<Data>{
        public Data data;

        public Success(Data data)
        {
            this.data = data;
        }
    }

    public static class Loading<Data> extends Result<Data>{

    }

    public static <Data> Success<Data> Success(Data data)
    {
        return new Success<Data>(data);
    }

    public static Loading Loading()
    {
        return new Loading();
    }

}
