package com.flannery.whale;

import android.os.AsyncTask;

//https://blog.csdn.net/lmj623565791/article/details/38614699
// 要点：
// 使用线程池复用
// 有一个阻塞队列
// 通过handler
// 使用FutureTask做异步执行
public class MyAsyncTaskDemo {

    void test() {
        new MAsyncTask().execute();
    }

    class MAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            publishProgress(1);
            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }
    }

}
