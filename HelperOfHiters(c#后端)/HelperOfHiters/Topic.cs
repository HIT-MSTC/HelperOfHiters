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
        public static bool PostTopic(String Author,String Title,String Part,String Text)
        {
            String commStr = "insert into Topic (Author,Date,Title,Part,Text) values ('" + Author + "',getdate(),'"+ Title + "','" + Part + "','" + Text + "')";
            SQL sql = new SQL();
            bool f = sql.ExecuteNonQuery(commStr);
            sql.Dispose();
            return f;
        }
        public static bool DeleteTopic(int TopicId)
        {
            String commStr = "delete from Topic where id = "+TopicId;
            SQL sql = new SQL();
            bool f = sql.ExecuteNonQuery(commStr);
            sql.Dispose();
            return f;
        }
        public static String GetAllRecommandTopic()
        {
            String commStr = "select * from Topic where Recommand = 1";
            SQL sql = new SQL();
            String ans = sql.ExecuteXml(commStr);
            sql.Dispose();
            return ans;
        }
        public static String GetRecommandTopic(String Part)
        {
            String commStr = "select * from Topic where Recommand = 1 and Part in ('" + Part.Substring(0,Part.Length-1).Replace(",","','") + "')";
            SQL sql = new SQL();
            String ans = sql.ExecuteXml(commStr);
            sql.Dispose();
            return ans;
        }
        public static String GetPartTopic(String Part)
        {
            String commStr = "select * from Topic where Part = '" + Part + "'";
            SQL sql = new SQL();
            String ans = sql.ExecuteXml(commStr);
            sql.Dispose();
            return ans;
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