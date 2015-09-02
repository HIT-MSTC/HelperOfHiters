using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;

namespace HelperOfHiters
{
    public class Topic
    {
        public static int PostTopic(String Author,String Title,String Part,String Text)
        {
            String commStr1 = "select max(id) as lastId from Topic";
            SQL sql = new SQL();
            SqlDataReader r = sql.ExecuteReader(commStr1);
            int id = 0;
            if(r.Read())
                id = int.Parse(r[0].ToString())+1;
            r.Close();
            String date = DateTime.Now.ToShortDateString();
            String commStr2 = "insert into Topic values (" + id + ",'" + Author + "','" + date + "','"+ Title + "','" + Part + "','" + Text + "')";
            sql.ExecuteNonQuery(commStr2);
            sql.Dispose();
            return id;
        }
        public static bool DeleteTopic(int TopicId)
        {
            String commStr = "delete from Topic where id = "+TopicId;
            SQL sql = new SQL();
            bool f = sql.ExecuteNonQuery(commStr);
            sql.Dispose();
            //if (f)
                //f = Answer.DeleteTopicAnswer(TopicId);
            return f;
        }
        public static bool ModifyMessage(int TopicId, String key, String values)
        {
            String commStr = "update Topic set " + key + " = '" + values + "' where id = " + TopicId;
            SQL sql = new SQL();
            bool f = sql.ExecuteNonQuery(commStr);
            sql.Dispose();
            return f;
        }
        public static String GetMessage(int id, String key)
        {
            String ans = "";
            String commStr = "select " + key + " from Topic where id = " + id;
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
            String commStr = "select * from Topic where id = " + id;
            SQL sql = new SQL();
            String ans = sql.ExecuteXml(commStr);
            sql.Dispose();
            return ans;
        }
        public static String GetUserTopic(String Author)
        {
            String commStr = "select * from Topic where Author = '" + Author + "'";
            SQL sql = new SQL();
            String ans = sql.ExecuteXml(commStr);
            sql.Dispose();
            return ans;
        }
    }
}