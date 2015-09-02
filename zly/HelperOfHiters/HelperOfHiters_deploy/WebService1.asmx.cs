using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace HelperOfHiters
{
    /// <summary>
    /// WebService1 的摘要说明
    /// </summary>
    [WebService(Namespace = "http://192.168.17.185/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消注释以下行。 
     [System.Web.Script.Services.ScriptService]
    public class WebService1 : System.Web.Services.WebService
    {
        [WebMethod]
        public bool Register(String UserName, String Password)
        {
            return Users.Register(UserName, Password);
        }

        [WebMethod]
        public bool Login(String UserName, String Password)
        {
            return Users.Login(UserName, Password);
        }

        [WebMethod]
        public bool ModifyUserPassword(String UserName, String Password)
        {
            return Users.ModifyMessage(UserName, "Password", Password);
        }

        [WebMethod]
        public bool ModifyUserEmail(String UserName, String Email)
        {
            return Users.ModifyMessage(UserName, "Email", Email);
        }

        [WebMethod]
        public bool ModifyUserStudentNumber(String UserName, String StudentNumber)
        {
            return Users.ModifyMessage(UserName, "StudentNumber", StudentNumber);
        }

        [WebMethod]
        public bool ModifyUserMajor(String UserName, String Major)
        {
            return Users.ModifyMessage(UserName, "Major", Major);
        }

        [WebMethod]
        public String GetUserEmail(String UserName)
        {
            return Users.GetMessage(UserName, "Email");
        }

        [WebMethod]
        public String GetUserStudentNumber(String UserName)
        {
            return Users.GetMessage(UserName, "StudentNumber");
        }

        [WebMethod]
        public String GetUserMajor(String UserName)
        {
            return Users.GetMessage(UserName, "Major");
        }

        [WebMethod]
        public String GetUserAccumulatePoint(String UserName)
        {
            return Users.GetMessage(UserName, "AccumulatePoint");
        }

        [WebMethod]
        public String GetUserFocusPart(String UserName)
        {
            return Users.GetMessage(UserName, "FocusPart");
        }

        [WebMethod]
        public bool AddUserFocusPart(String UserName, String Part)
        {
            return Users.AddFocusPart(UserName, Part);
        }

        [WebMethod]
        public bool DeleteUserFocusPart(String UserName, String Part)
        {
            return Users.DeleteFocusPart(UserName, Part);
        }

        [WebMethod]
        public bool AddUserPoint(String UserName, int Point)
        {
            return Users.AddPoint(UserName, Point);
        }

        [WebMethod]
        public String GetUserXml(String UserName)
        {
            return Users.GetXml(UserName);
        }

        [WebMethod]
        public int PostTopic(String Author, String Title, String Part, String Text)
        {
            return Topic.PostTopic(Author, Title, Part, Text); 
        }

        [WebMethod]
        public bool DeleteTopic(int TopicId)
        {
            return Topic.DeleteTopic(TopicId);
        }

        [WebMethod]
        public String GetUserTopic(String Author)
        {
            return Topic.GetUserTopic(Author);
        }

        [WebMethod]
        public bool ModifyTopicTitle(int TopicId, String Title)
        {
            return Topic.ModifyMessage(TopicId, "Title", Title);
        }

        [WebMethod]
        public bool ModifyTopicPart(int TopicId, String Part)
        {
            return Topic.ModifyMessage(TopicId, "Part", Part);
        }

        [WebMethod]
        public bool ModifyTopicText(int TopicId, String Text)
        {
            return Topic.ModifyMessage(TopicId, "Text", Text);
        }

        [WebMethod]
        public String GetTopicAuthor(int TopicId)
        {
            return Topic.GetMessage(TopicId, "Author");
        }

        [WebMethod]
        public String GetTopicDate(int TopicId)
        {
            return Topic.GetMessage(TopicId, "Date");
        }

        [WebMethod]
        public String GetTopicTitle(int TopicId)
        {
            return Topic.GetMessage(TopicId, "Title");
        }

        [WebMethod]
        public String GetTopicPart(int TopicId)
        {
            return Topic.GetMessage(TopicId, "Part");
        }

        [WebMethod]
        public String GetTopicText(int TopicId)
        {
            return Topic.GetMessage(TopicId, "Text");
        }

        [WebMethod]
        public String GetTopicXml(int TopicId)
        {
            return Topic.GetXml(TopicId);
        }

        [WebMethod]
        public int PostAnswer(int TopicId, String Author, String Text)
        {
            return Answer.PostAnswer(TopicId, Author, Text);
        }

        [WebMethod]
        public bool DeleteAnswer(int id)
        {
            return Answer.DeleteAnswer(id);
        }

        [WebMethod]
        public String GetTopicAnswer(int TopicId)
        {
            return Answer.GetTopicAnswer(TopicId);
        }

        [WebMethod]
        public String GetUserAnswer(String Author)
        {
            return Answer.GetUserAnswer(Author);
        }

        [WebMethod]
        public bool ModifyAnswerText(int id, String Text)
        {
            return Answer.ModifyMessage(id, "Text", Text);
        }

        [WebMethod]
        public String GetAnswerTopic(int id)
        {
            return Answer.GetMessage(id, "TopicId");
        }

        [WebMethod]
        public String GetAnswerAuthor(int id)
        {
            return Answer.GetMessage(id, "Author");
        }

        [WebMethod]
        public String GetAnswerDate(int id)
        {
            return Answer.GetMessage(id, "Date");
        }

        [WebMethod]
        public String GetAnswerText(int id)
        {
            return Answer.GetMessage(id, "Text");
        }

        [WebMethod]
        public String GetAnswerXml(int id)
        {
            return Answer.GetXml(id);
        }
    }
}
