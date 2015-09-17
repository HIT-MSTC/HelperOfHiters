using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data;
using System.Data.SqlClient;
using System.Xml;

namespace HelperOfHiters
{
    public class SQL
    {
        public SqlConnection sqlCon;
        private String connectString = System.Configuration.ConfigurationManager.ConnectionStrings["connStr"].ConnectionString;
        public SQL()
        {
            if(sqlCon==null)
            {
                sqlCon = new SqlConnection();
                sqlCon.ConnectionString = connectString;
                sqlCon.Open();
            }
        }
        public bool ExecuteNonQuery(String commStr)
        {
            try
            {
                SqlCommand comm = sqlCon.CreateCommand();
                comm.CommandText = commStr;
                comm.ExecuteNonQuery();
            }
            catch (Exception e)
            {
                return false;
            }
            return true;
        }
        public SqlDataReader ExecuteReader(String commStr)
        {
            try
            {
                SqlCommand comm = sqlCon.CreateCommand();
                comm.CommandText = commStr;
                return comm.ExecuteReader();
            }
            catch (Exception e)
            {
                return null;
            }
        }
        public String ExecuteXml(String commStr)
        {
            try
            {
                SqlDataAdapter da = new SqlDataAdapter(commStr, sqlCon);
                DataSet ds = new DataSet();
                da.Fill(ds);
                return ds.GetXml();
            }
            catch (Exception e)
            {
                return null;
            }
        }
        public void Dispose()
        {
            if(sqlCon!=null)
            {
                sqlCon.Close();
                sqlCon = null;
            }
        }
    }
}