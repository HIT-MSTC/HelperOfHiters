using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;

namespace HelperOfHiters
{
    public class Answer
    {
        public static bool ModifyMessage(int id, String key, String values)
        {
            String commStr = "update Answer set " + key + " = '" + values + "' where id = " + id;
            SQL sql = new SQL();
            bool f = sql.ExecuteNonQuery(commStr);
            sql.Dispose();
            return f;
        }
        public static int PostAnswer(int TopicId,String Author,String Text)
        {
            String commStr1 = "select max(id) as lastId from Answer";
            SQL sql = new SQL();
            SqlDataReader r = sql.ExecuteReader(commStr1);
            int id = 0;
            if (r.Read())
                id = int.Parse(r[0].ToString()) + 1;
            r.Close();
            String date = DateTime.Now.ToShortDateString();
            String commStr2 = "insert into Answer values (" + id + "," + TopicId + ",'" + Author + "','" + date + "','" + Text + "')";
            sql.ExecuteNonQuery(commStr2);
            sql.Dispose();
            return id;
        }
        public static bool DeleteAnswer(int id)
        {
            String commStr = "delete from Answer where id = " + id;
            SQL sql = new SQL();
            bool f = sql.ExecuteNonQuery(commStr);
            sql.Dispose();
            return f;
        }

        /*public static bool DeleteTopicAnswer(int TopicId)
        {
            String commStr = "delete from Answer where TopicId = " + TopicId;
            SQL sql = new SQL();
            bool f = sql.ExecuteNonQuery(commStr);
            sql.Dispose();
            return f;
        }*/

        public static String GetMessage(int id, String key)
        {
            String ans = "";
            String commStr = "select " + key + " from Answer where id = " + id;
            SQL sql = new SQL();
            SqlDataReader r = sql.ExecuteReader(commStr);
            if (r == null)
                return ans;
            if (r.Read())
                ans += r[0].ToString();
            r.Close();
            sql.Dispose();
            return ans;
        }
        public static String GetXml(int id)
        {
            String commStr = "select * from Answer where id = "+id;
            SQL sql = new SQL();
            String ans = sql.ExecuteXml(commStr);
            sql.Dispose();
            return ans;
        }
        public static String GetTopicAnswer(int TopicId)
        {
            String commStr = "select * from Answer where TopicId = " + TopicId;
            SQL sql = new SQL();
            String ans = sql.ExecuteXml(commStr);
            sql.Dispose();
            return ans;
        }
        public static String GetUserAnswer(String Author)
        {
            String commStr = "select * from Answer where Author = '" + Author + "'";
            SQL sql = new SQL();
            String ans = sql.ExecuteXml(commStr);
            sql.Dispose();
            return ans;
        }
    }
}