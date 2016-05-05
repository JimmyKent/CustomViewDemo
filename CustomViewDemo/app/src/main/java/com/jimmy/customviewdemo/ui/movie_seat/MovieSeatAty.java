package com.jimmy.customviewdemo.ui.movie_seat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jimmy.customviewdemo.R;

import java.util.Random;


public class MovieSeatAty extends AppCompatActivity {

    // 0 - 空位; 1 - 已售; 2 -  选中; -1 - 过道
    public static final int[] SEAT_TYPE = {-1, 0, 1, 2};

    public static enum SEAT_TYPE_IMG {
        empty(0, R.drawable.seat_empty), hallway(-1, R.drawable.seat_hallway), selled(1, R.drawable.seat_selled), selected(2, R.drawable.seat_select);

        int type = -1;
        int imgRes = 0;

        SEAT_TYPE_IMG(int type, int imgRes) {
            this.type = type;
            this.imgRes = imgRes;
        }

        public static int getImgRes(int type) {
            int imgRes = 0;
            for (SEAT_TYPE_IMG u : SEAT_TYPE_IMG.values()) {
                if (type == u.type) {
                    return u.imgRes;
                }
            }
            return imgRes;
        }
    }

    private int[][] mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_movie_seat);
        initData();
        MoviewSeatView moviewSeatView = (MoviewSeatView) findViewById(R.id.btn_movie_seat);
        moviewSeatView.setData(mData);
    }

    private void initData() {
        int row = 10;// 10排
        int culomn = 10;// 10行
        mData = new int[row][culomn];
        int i = 0, j = 0;
        Random random = null;
        while (i < row) {
            j = 0;
            while (j < culomn) {
                random = new Random();
                mData[i][j] = SEAT_TYPE[random.nextInt(SEAT_TYPE.length)];
                j++;
            }
            i++;
        }
    }

    public void printArray(int[][] arr) {

        for (int i = 0; i < arr.length; i++) {
            System.out.print("[");
            for (int j = 0; j < arr[i].length; j++) {
                if (j != arr[i].length - 1)
                    System.out.print(arr[i][j] + ",");
                else
                    System.out.print(arr[i][j]);
            }
            System.out.println("]");
        }

    }


}
