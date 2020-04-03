package com.e.play;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


class Board
{


    static char a[][]=new char[8][8];
    Board()
    {
        int i,j;
        for(i=0;i<8;++i)
            for(j=0;j<8;++j)
                a[i][j]=' ';

        a[0][0]='E';
        a[0][1]='H';
        a[0][2]='C';
        a[0][3]='W';
        a[0][4]='K';
        a[0][5]='C';
        a[0][6]='H';
        a[0][7]='E';

        a[7][0]='e';
        a[7][1]='h';
        a[7][2]='c';
        a[7][3]='k';
        a[7][4]='w';
        a[7][5]='c';
        a[7][6]='h';
        a[7][7]='e';

        for(j=0;j<8;++j)
        {
            a[1][j]='I';
            a[6][j]='i';
        }
    }//constructor



    void makemove(int x1,int y1,int x2,int y2)
    {
        a[x2][y2]=a[x1][y1];
        a[x1][y1]=' ';
    }//makemove


    public static boolean win(char key)
    {
        int i=0,j=0;
        for(i=0;i<8;++i)
        {
            for(j=0;j<8;++j)
            {
                if(a[i][j]==key)
                {
                    return  false;
                }
            }
        }

        return true;
    }//win


}//board



class Check extends Board
{


    boolean checke(int x1,int y1,int x2,int y2)
    {
        int i,j;
        int dx=x2-x1,dy=y2-y1,xin,yin;
        if(dx==0||dy==0)
        {
            if(dy==0)
            {
                if(dx>0)
                    xin=1;
                else
                    xin=-1;
                i=x1+xin;
                while(i!=x2)
                {
                    if(a[i][y1]!=' ')
                    {
                        return false;
                    }
                    i=i+xin;
                }
                return true;
            }
            else
            {
                if(dy>0)
                    yin=1;
                else
                    yin=-1;
                i=y1+yin;
                while(i!=y2)
                {
                    if(a[x1][i]!=' ')
                    {
                        return false;
                    }
                    i=i+yin;
                }
                return true;
            }
        }

        return false;

    }//checke

    boolean checkh(int x1,int y1,int x2,int y2)
    {

        if(x2==x1-1||x2==x1+1)
        {
            if(y2==y1+2||y2==y1-2)
                return true;


        }

        else if(x2==x1+2||x2==x1-2)
        {
            if(y2==y1-1||y2==y1+1)
                return true;


        }


        return false;
    }//checkh

    boolean checkc(int x1,int y1,int x2,int y2)
    {
        int dx=x2-x1,dy=y2-y1,xin,yin,i,j;
        if(dx+dy==0||2*dx==dx+dy)
        {
            if(dx>0)
                xin=1;
            else
                xin=-1;
            if(dy>0)
                yin=1;
            else
                yin=-1;
            i=x1+xin;
            j=y1+yin;

            while(i!=x2&&j!=y2)
            {
                if(a[i][j]!=' ')
                {
                    return false;
                }
                i=i+xin;
                j=j+yin;
            }
            return true;
        }

        else
            return false;

    }//checkc

    boolean checkw(int x1,int y1,int x2,int y2)
    {
        int dx=x2-x1,dy=y2-y1,incx,incy,i;
        boolean flag1,flag2;


        if(x2==x1||y2==y1)
        {
            flag1=checke(x1,y1,x2,y2);
            return flag1;
        }

        else if(dx+dy==0||2*dx==dx+dy)
        {
            flag2=checkc(x1,y1,x2,y2);
            return flag2;
        }

        else
            return false;

    }//checkw

    boolean checkk(int x1,int y1,int x2,int y2)
    {

        if(x2==x1||x2==x1+1||x2==x1-1)
        {
            if(y2==y1||y2==y1+1||y2==y1-1)
            {
                return true;
            }
            else
                return false;
        }

        return false;

    }//checkk



}// check





class Cplayer1 extends Check {



    boolean checkself(int x1, int y1) {
        char key = a[x1][y1];

        switch (key)
        {
            case 'i':
            case 'e':
            case 'h':
            case 'c':
            case 'w':
            case 'k':
                return true;
        }
        return false;
    }//checkself

    boolean checkbo(int x2, int y2)
    {
        if (a[x2][y2] == ' ')
            return true;
        char key = a[x2][y2];

        switch (key) {
            case 'I':
            case 'E':
            case 'H':
            case 'C':
            case 'W':
            case 'K':
                return true;
        }

        return false;
    }//chechbo


    boolean checkcanpath(int x1, int y1, int x2, int y2,Player1 p1,Player2 p2) {
        char key = a[x1][y1];
        char key1=a[x2][y2];
        boolean flag=false;
        switch (key)
        {
            case 'i':
                flag = checki(x1, y1, x2, y2);
                break;
            case 'e':
                flag = checke(x1, y1, x2, y2);
                break;
            case 'h':
                flag = checkh(x1, y1, x2, y2);
                break;
            case 'c':
                flag = checkc(x1, y1, x2, y2);
                break;
            case 'w':
                flag = checkw(x1, y1, x2, y2);
                break;
            case 'k':
                flag = checkk(x1, y1, x2, y2);
                break;
        }
       if(flag )
        {// not allow to go if king in danger
            a[x2][y2] = key;
            a[x1][y1] = ' ';
            flag = p2.check();
            a[x2][y2] = key1;
            a[x1][y1] = key;
            if(flag==false)
            {flag=true;}
            else
            { flag=false; }
        }

        return flag;
    }//checkcanpath


    ////////////////////
    boolean checkcanpathafter(int x1, int y1, int x2, int y2) {
        char key = a[x1][y1];
        boolean flag=false;
        switch (key) {
            case 'i':
                flag = checki(x1, y1, x2, y2);
                break;
            case 'e':
                flag = checke(x1, y1, x2, y2);
                break;
            case 'h':
                flag = checkh(x1, y1, x2, y2);
                break;
            case 'c':
                flag = checkc(x1, y1, x2, y2);
                break;
            case 'w':
                flag = checkw(x1, y1, x2, y2);
                break;
            case 'k':
                flag = checkk(x1, y1, x2, y2);
                break;
        }

        return flag;
    }//checkcanpath
    /////////////////////

    boolean checki(int x1, int y1, int x2, int y2) {
        if (x2 == x1 - 1) {
            if (y2 == y1 && a[x2][y2] == ' ')
                return true;
            else if (((y2 == y1 + 1) && (a[x2][y2] != ' ')) || ((y2 == y1 - 1) && (a[x2][y2] != ' ')))
                return true;
        }
        return false;
    }//checki



    boolean check()
    {
        boolean flag1=true;
        int i,j,m=0,n=0;
        for(i=0;i<8;++i)
        {
            for(j=0;j<8;++j)
            {
                if(a[i][j]=='K')
                {
                    m=i;
                    n=j;
                    break;
                }
            }
        }

        for(i=0;i<8;++i)
        {
            for(j=0;j<8;++j)
            {
                flag1=checkself(i,j);
                if(!flag1)
                    continue;


                flag1=checkcanpathafter(i,j,m,n);
                if(!flag1)
                    continue;

                return true;
            }
        }

        return false;

    }//ckecck
}


class Cplayer2 extends Check
{



    boolean checkself(int x1,int y1)
    {
        char key=a[x1][y1];
        switch(key)
        {
            case 'I':
            case 'E':
            case 'H':
            case 'C':
            case 'W':
            case 'K':
                return true;
        }
        return false;
    }//checkself

    boolean checkbo(int x2,int y2)
    {
        if(a[x2][y2]==' ')
            return true;
        char key=a[x2][y2];

        switch(key)
        {
            case 'i':
            case 'e':
            case 'h':
            case 'c':
            case 'w':
            case 'k':
                return true;
        }

        return false;
    }//chechbo


    boolean checkcanpath(int x1,int y1,int x2,int y2,Player1 p1,Player2 p2)
    {
        char key=a[x1][y1];
        char key1=a[x2][y2];
        boolean flag=false;
        switch(key)
        {
            case 'I':
                flag=checki(x1,y1,x2,y2);
                break;
            case 'E':
                flag=checke(x1,y1,x2,y2);
                break;
            case 'H':
                flag=checkh(x1,y1,x2,y2);
                break;
            case 'C':
                flag=checkc(x1,y1,x2,y2);
                break;
            case 'W':
                flag=checkw(x1,y1,x2,y2);
                break;
            case 'K':
                flag=checkk(x1,y1,x2,y2);
                break;
        }


        if(flag ) {
            a[x2][y2] = key;
            a[x1][y1] = ' ';
            flag = p1.check();
            a[x2][y2] = key1;
            a[x1][y1] = key;
            if(flag==false)
            {flag=true;}
            else
            { flag=false; }
        }

        return flag;
    }//checkcanpath

    ////////////
    boolean checkcanpathafter(int x1,int y1,int x2,int y2)
    {
        char key=a[x1][y1];
        boolean flag=false;
        switch(key)
        {
            case 'I':
                flag=checki(x1,y1,x2,y2);
                break;
            case 'E':
                flag=checke(x1,y1,x2,y2);
                break;
            case 'H':
                flag=checkh(x1,y1,x2,y2);
                break;
            case 'C':
                flag=checkc(x1,y1,x2,y2);
                break;
            case 'W':
                flag=checkw(x1,y1,x2,y2);
                break;
            case 'K':
                flag=checkk(x1,y1,x2,y2);
                break;
        }

        return flag;
    }//checkcanpath
    ////////////



    boolean checki(int x1,int y1,int x2,int y2)
    {
        if(x2==x1+1)
        {
            if(y2==y1&&a[x2][y2]==' ')
                return true;
            else if(((y2==y1+1) && (a[x2][y2]!=' '))||((y2==y1-1) && (a[x2][y2]!=' ')))
                return true;
        }
        return false;
    }//checki



    boolean check()
    {
        boolean flag1=true;
        int i,j,m=0,n=0;
        for(i=0;i<8;++i)
        {
            for(j=0;j<8;++j)
            {
                if(a[i][j]=='k')
                {
                    m=i;
                    n=j;
                    break;
                }
            }
        }

        for(i=0;i<8;++i)
        {
            for(j=0;j<8;++j)
            {
                flag1=checkself(i,j);
                if(!flag1)
                    continue;


                flag1=checkcanpathafter(i,j,m,n);
                if(!flag1)
                    continue;

                return true;
            }
        }

        return false;

    }//ckecck

}//Cplayer2





class Player1 extends Cplayer1
{
    int x1,y1,x2,y2;
    private TextView output;

    boolean flag=false,flag1=false;



    public boolean checkmate(Player1 p1,Player2 p2)
    {
        flag=false;
        flag1=false;
        int i,j,x,y;

        for(i=0;i<a.length;++i)
        {
            for(j=0;j<a.length;++j)
            {
                for(x=0;x<a.length;++x)
                {
                    for (y=0;y<a.length;++y)
                    {
                        flag=checkself(i,j);
                        if(!flag)
                            continue;

                        flag=checkbo(x,y);
                        if(!flag)
                            continue;

                        flag=checkcanpath(i,j,x,y,p1,p2);
                        if(!flag)
                            continue;
                        return false;

                    }

                }

            }

        }
        return  true;

    }


    public  boolean get1(int x,int y)
    {

        flag = checkself(x, y);
        if(flag) {
            x1 = x;
            y1 = y;
            //output.setText("self Ok");
        }
        else
        {
            // output.setText("not your sepoi");
        }
        return flag;
    }

    public  boolean get2(int x,int y,Player1 p1,Player2 p2)
    {
        flag = checkbo(x,y);

        if(flag)
        {
            x2 = x;
            y2 = y;
            flag = checkcanpath(x1, y1, x2, y2,p1,p2);
        }
        if(flag)
    {
        makemove(x1,y1,x2,y2);
        return true;
    }

        return false;

    }

}//Player1





class Player2 extends Cplayer2
{

    int x1,y1,x2,y2;
    private TextView output;
    boolean flag=false,flag1=false;

    public boolean checkmate(Player1 p1,Player2 p2)
    {
        flag=false;
        flag1=false;
        int i,j,x,y;

        for(i=0;i<a.length;++i)
        {
            for(j=0;j<a.length;++j)
            {
                for(x=0;x<a.length;++x)
                {
                    for (y=0;y<a.length;++y)
                    {
                        flag=checkself(i,j);
                        if(!flag)
                            continue;

                        flag=checkbo(x,y);
                        if(!flag)
                            continue;

                        flag=checkcanpath(i,j,x,y,p1,p2);
                        if(!flag)
                            continue;
                        return false;

                    }

                }

            }

        }
        return  true;

    }



    public  boolean get1(int x,int y)
    {
        flag = checkself(x, y);
        if(flag) {
            //  output.setText("self Ok");
            x1 = x;
            y1 = y;
        }
        else
        {
            //  output.setText("not your sepoi");
        }
        return flag;
    }

    public  boolean get2(int x,int y,Player1 p1,Player2 p2)
    {
        flag = checkbo(x,y);
        if(flag) {
            x2 = x;
            y2 = y;
            flag=checkcanpath(x1,y1,x2,y2,p1,p2);
            if(!flag)
            {
                // output.setText("Can't reach");
            }
        }
        else
        {
            //  output.setText("Cant reach");
        }
        if(flag)
        {makemove(x1,y1,x2,y2);}

        return flag;
    }
}//Player2





//*************************************************************************************


public class playchess extends AppCompatActivity {



    private ImageButton[][]  button=new ImageButton[8][8];
    private  TextView output;
    private  TextView result;



    private int i=0,j=0;
    int d=0,k=0;
    Player1 p1=new Player1();
    Player2 p2=new Player2();






    void set1(int x1,int y1)
    {
        boolean flag=false;
        if(d==0)
        {
            flag=p1.get1(x1,y1);
        }

        if(d==1)
        {
            flag = p2.get1(x1, y1);
        }
        if(flag)
        {
            k=1;
        }
        else{
            k=0;
        }


    }


    void set2(int x2,int y2)
    {
        int c;
        result=findViewById(R.id.result);
        output=findViewById(R.id.output);

        boolean flag=false,flag2=false,gameover=false;
        if(d==0)
        {
            flag=p1.get2(x2,y2,p1,p2);
            if(flag)
                gameover=p2.checkmate(p1,p2);
        }

        if(d==1)
        {
            flag = p2.get2(x2, y2,p1,p2);
            if(flag)
                gameover=p1.checkmate(p1,p2);
        }




        if(flag)
        {
            // result.setText("move  ok");

            reflect();
            if(d==0)
            {
                c=2;
            }
            else
            {
                c=1;
            }

            if(d==0)
            {
                flag2=p1.check();
            }
            else
            {
                flag2=p2.check();
            }
            if(flag2)
            {
                result.setText(" king Indanger");
            }



            else
            {
                result.setText("" );
            }


            flag2=win1(d);
            if(flag2)
            {
                result.setText("Player "+(d+1)+" wins");
                output.setText(" ");
            }
            d++;
            d=d%2;

            if(d==0 )
            {
                output.setText("Player 1 ");
            }
            if(d==1 )
            {
                output.setText("Player 2 ");
            }
            if(flag2)
            {
                output.setText(" ");
            }

            k=0;

            if(gameover)
            {
                result.setText("Check mate");
                block();
            }
        }
        else
        {
            k=0;
            // result.setText("move not ok");
        }
    }


    boolean win1(int d)
    {

        output=findViewById(R.id.output);
        int i=0,j=0;
        int x,y;
        char key;
        boolean flag;
        if(d==0)
        {
            key='K';
        }
        else
        {
            key='k';
        }


        flag=Board.win(key);
        if(flag)
        {
            block();
            return true;
        }
        return false;

    }

    void block()
    {
        int i,j;
        for (i = 0; i < 8; ++i) {
            for (j = 0; j < 8; ++j)
            {
                button[i][j].setEnabled(false);
            }
        }

    }


    public void reflect()
    {
        String rot;
        char key;
        int i,j,d;
        for(i=0;i<8;++i)
        {
            for(j=0;j<8;++j)
            {
                d=i+j;
                d=d%2;
                key=Board.a[i][j];
                switch (key)
                {
                    case ' ':
                        if(d==0)
                            button[i][j].setImageResource(R.drawable.chesswhite);
                        else
                            button[i][j].setImageResource(R.drawable.chessblack);
                        break;
                    case 'i':
                        if(d==0)
                            button[i][j].setImageResource(R.drawable.wiw);
                        else
                            button[i][j].setImageResource(R.drawable.wib);
                        break;

                    case 'e':
                        if(d==0)
                            button[i][j].setImageResource(R.drawable.wew);
                        else
                            button[i][j].setImageResource(R.drawable.web);
                        break;

                    case 'h':
                        if(d==0)
                            button[i][j].setImageResource(R.drawable.whw);
                        else
                            button[i][j].setImageResource(R.drawable.whb);
                        break;
                    case 'c':
                        if(d==0)
                            button[i][j].setImageResource(R.drawable.wcw);
                        else
                            button[i][j].setImageResource(R.drawable.wcb);
                        break;
                    case 'w':
                        if(d==0)
                            button[i][j].setImageResource(R.drawable.www);
                        else
                            button[i][j].setImageResource(R.drawable.wwb);
                        break;

                    case 'k':
                        if(d==0)
                            button[i][j].setImageResource(R.drawable.wkw);
                        else
                            button[i][j].setImageResource(R.drawable.wkb);
                        break;
                    case 'I':
                        if(d==0)
                            button[i][j].setImageResource(R.drawable.biw);
                        else
                            button[i][j].setImageResource(R.drawable.bib);
                        break;
                    case 'E':
                        if(d==0)
                            button[i][j].setImageResource(R.drawable.bew);
                        else
                            button[i][j].setImageResource(R.drawable.beb);
                        break;
                    case 'H':
                        if(d==0)
                            button[i][j].setImageResource(R.drawable.bhw);
                        else
                            button[i][j].setImageResource(R.drawable.bhb);
                        break;
                    case 'C':
                        if(d==0)
                            button[i][j].setImageResource(R.drawable.bcw);
                        else
                            button[i][j].setImageResource(R.drawable.bcb);
                        break;
                    case 'W':
                        if(d==0)
                            button[i][j].setImageResource(R.drawable.bww);
                        else
                            button[i][j].setImageResource(R.drawable.bwb);
                        break;
                    case 'K':
                        if(d==0)
                            button[i][j].setImageResource(R.drawable.bkw);
                        else
                            button[i][j].setImageResource(R.drawable.bkb);
                        break;

                }



            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playchess);
        output=findViewById(R.id.output);



        String rot;
        for (i = 0; i < 8; ++i) {
            for (j = 0; j < 8; ++j)
            {
                rot = "imageButton" + i + j;
                int resid = getResources().getIdentifier(rot, "id", getPackageName());
                button[i][j] = findViewById(resid);
            }
        }



        d=0;// player 1 player 2
        k=0;// initial or final
        reflect();;









        button[0][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                boolean flag=false;
                int x=0,y=0;

                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[0][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;

                int x=0,y=1;

                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[0][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=0,y=2;

                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[0][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=0,y=3;

                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[0][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=0,y=4;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[0][5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=0,y=5;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[0][6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=0,y=6;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[0][7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=0,y=7;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });

        button[1][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=1,y=0;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[1][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=1,y=1;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[1][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=1,y=2;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[1][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=1,y=3;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[1][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=1,y=4;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[1][5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=1,y=5;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[1][6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=1,y=6;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[1][7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=1,y=7;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[2][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=2,y=0;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[2][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=2,y=1;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[2][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=2,y=2;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[2][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=2,y=3;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[2][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=2,y=4;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[2][5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=2,y=5;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[2][6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=2,y=6;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[2][7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=2,y=7;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[3][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=3,y=0;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[3][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=3,y=1;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[3][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=3,y=2;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[3][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=3,y=3;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[3][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=3,y=4;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[3][5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=3,y=5;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[3][6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=3,y=6;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[3][7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=3,y=7;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[4][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=4,y=0;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[4][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=4,y=1;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[4][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=4,y=2;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[4][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=4,y=3;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[4][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=4,y=4;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[4][5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=4,y=5;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[4][6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=4,y=6;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[4][7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=4,y=7;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[5][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=5,y=0;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[5][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=5,y=1;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[5][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=5,y=2;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[5][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=5,y=3;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[5][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=5,y=4;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[5][5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=5,y=5;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[5][6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=5,y=6;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[5][7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=5,y=7;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[6][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=6,y=0;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[06][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=6,y=1;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[6][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=6,y=2;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[6][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=6,y=3;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[6][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=6,y=4;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[6][5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=6,y=5;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[6][6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=6,y=6;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[6][7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=6,y=7;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[7][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=7,y=0;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[7][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=7,y=1;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[7][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=7,y=2;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[7][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=7,y=3;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[7][4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=7,y=4;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[7][5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=7,y=5;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[7][6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=7,y=6;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });
        button[7][7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean flag=false;
                int x=7,y=7;
                if(k==0)
                {
                    set1(x,y);
                }
                else if(k==1)
                {
                    set2(x,y);
                }


            }
        });

    }
}
