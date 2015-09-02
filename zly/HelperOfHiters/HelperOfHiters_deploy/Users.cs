using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;

namespace HelperOfHiters
{
    public class Users
    {
        public static bool Register(String UserName,String Password)
        {
            String commStr = "insert into Users ([UserName],Password) values ('" + UserName + "','" + Password + "')";
            SQL sql = new SQL();
            bool f = sql.ExecuteNonQuery(commStr);
            sql.Dispose();
            return f;
        }
        public static bool Login(String UserName,String Password)
        {
            String commStr = "select * from Users where UserName = '" + UserName + "' and Password = '" + Password + "'";
            SQL sql = new SQL();
            bool f = sql.ExecuteReader(commStr).Read();
            sql.Dispose();
            return f;
        }
        public static bool ModifyMessage(String UserName,String key,String values)
        {
            String commStr = "update Users set " + key + " = '" + values + "' where UserName = '" + UserName + "'";
            SQL sql = new SQL();
            bool f = sql.ExecuteNonQuery(commStr);
            sql.Dispose();
            return f;
        }
        public static String GetMessage(String UserName,String key)
        {
            String ans = "";
            String commStr = "select " + key + " from Users where UserName = '" + UserName + "'";
            SQL sql = new SQL();
            SqlDataReader r = sql.ExecuteReader(commStr);
            if(r==null)
                return ans;
            if (r.Read())
                ans += r[0].ToString();
            r.Close();
            sql.Dispose();
            return ans;
        }
        public static bool AddFocusPart(String UserName, String Part)
        {
            String commStr = "update Users set FocusPart = '" + GetMessage(UserName, "FocusPart") + Part + ",' where UserName = '" + UserName + "'";
            SQL sql = new SQL();
            bool f = sql.ExecuteNonQuery(commStr);
            sql.Dispose();
            return f;
        }
        public static bool DeleteFocusPart(String UserName, String Part)
        {
            String commStr = "update Users set FocusPart = '" + GetMessage(UserName, "FocusPart").Replace(Part+",","") + "' where UserName = '" + UserName + "'";
            SQL sql = new SQL();
            bool f = sql.ExecuteNonQuery(commStr);
            sql.Dispose();
            return f;
        }
        public static bool AddPoint(String UserName, int Point)
        {
            String commStr = "update Users set AccumulatePoint = " + (int.Parse(GetMessage(UserName, "AccumulatePoint"))+Point) + " where UserName = '" + UserName + "'";
            SQL sql = new SQL();
            bool f = sql.ExecuteNonQuery(commStr);
            sql.Dispose();
            return f;
        }
        public static String GetXml(String UserName)
        {
            String commStr = "select UserName,Email,StudentNumber,Major,AccumulatePoint,FocusPart from Users where UserName = '" + UserName + "'";
            SQL sql = new SQL();
            String ans = sql.ExecuteXml(commStr);
            sql.Dispose();
            return ans;
        }
    }
}