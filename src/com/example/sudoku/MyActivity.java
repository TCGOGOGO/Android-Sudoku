package com.example.sudoku;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    static boolean flag;
    static int mp[][] = new int[10][10];
    public boolean ok(int pos, int cur)
    {
        int r = pos / 9;
        int c = pos % 9;
        for(int i = 0; i < 9; i++)
            if(mp[i][c] == cur)
                return false;
        for(int j = 0; j < 9; j++)
            if(mp[r][j] == cur)
                return false;
        int rr = r / 3 * 3;
        int cc = c / 3 * 3;
        for(int i = rr; i < rr + 3; i++)
            for(int j = cc; j < cc + 3; j++)
                if(mp[i][j] == cur)
                    return false;
        return true;
    }

    public void DFS(int pos)
    {
        if(flag || pos == 81)
        {
            flag = true;
            return;
        }
        int r = pos / 9;
        int c = pos % 9;
        if(mp[r][c] != 0)
        {
            DFS(pos + 1);
            if(flag)
                return;
        }
        else
        {
            for(int i = 1; i <= 9; i++)
            {
                if(ok(pos, i))
                {
                    mp[r][c] = i;
                    DFS(pos + 1);
                    if(flag)
                        return;
                }
                mp[r][c] = 0;
            }
        }
        return;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final EditText ev1 = (EditText) (findViewById(R.id.ev1));
        Button bt1 = (Button) findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView tv1 = (TextView) (findViewById(R.id.tv1));
                final String buf1 = ev1.getText().toString().trim();
                final String buf = buf1.replaceAll("\r|\n", "");
                String ans = "";
                for(int i = 0; i < buf.length(); i++)
                    mp[i / 9][i % 9] = Integer.parseInt(String.valueOf(buf.charAt(i)));
                //for(int i = 0; i < 9; i++)
                //    Log.d("debug", "num = " + String.valueOf(mp[0][i]));
                //tv1.setText(buf);
                flag = false;
                DFS(0);
                for(int i = 0; i < 81; i++)
                {
                    ans += String.valueOf(mp[i / 9][i % 9]);
                    ans += " ";
                    if((i + 1) % 9 == 0)
                        ans += '\n';
                }
                tv1.setText(ans);
            }
        });
    }
}
