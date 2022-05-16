using System;
using System.IO;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Azure.WebJobs;
using Microsoft.Azure.WebJobs.Extensions.Http;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;

namespace AzureFunc
{
    internal class TaskList
    {
        [FunctionName("CreateBusiness")]
        public static async Task<IActionResult> CreateTask(
           [HttpTrigger(AuthorizationLevel.Anonymous, "post", Route = "addbusiness")] HttpRequest req, ILogger log)
        {
            string requestBody = await new StreamReader(req.Body).ReadToEndAsync();
            var input = JsonConvert.DeserializeObject<BusinessModel>(requestBody);
            try
            {
                using (SqlConnection connection = new SqlConnection(Environment.GetEnvironmentVariable("SqlConnectionString")))
                {
                    connection.Open();
                    //if (String.IsNullOrEmpty(input.Description))
                    {
                        var query = $"INSERT INTO [tblBusiness] (BusinessName,BusType,City,PhoneNumber,Address,Country,OwnershipType, isWomenOwned,MBE, WBE, MWBE,LGBTBE, VOSB, VeteranOwned ) VALUES('{input.BusinessName}', '{input.BusType}' , '{input.City}','{input.PhoneNumber}','{input.Address}','{input.Country}','{input.OwnershipType}','{input.isWomenOwned}','{input.MBE}','{input.WBE}', '{input.MWBE}', '{input.LGBTBE}','{input.VOSB}','{input.VeteranOwned}')";
                        SqlCommand command = new SqlCommand(query, connection);
                        command.ExecuteNonQuery();

                    }
                }
            }
            catch (Exception e)
            {
                log.LogError(e.ToString());
                return new BadRequestResult();
            }
            return new OkResult();
        }

        [FunctionName("GetbusinessName")]
        public static IActionResult GetTaskByName(
       [HttpTrigger(AuthorizationLevel.Anonymous, "get", Route = "business/{Name}")] HttpRequest req, ILogger log, string name)
        {
            DataTable dt = new DataTable();
            try
            {
                using (SqlConnection connection = new SqlConnection(Environment.GetEnvironmentVariable("SqlConnectionString")))
                {
                    connection.Open();
                    var query = @"Select * from tblBusiness b join tblEmployees e on b.BusID=e.busID  Where businessName =  @Name";
                    SqlCommand command = new SqlCommand(query, connection);
                    command.Parameters.AddWithValue("@Name", name);
                    SqlDataAdapter da = new SqlDataAdapter(command);
                    da.Fill(dt);
                }
            }
            catch (Exception e)
            {
                log.LogError(e.ToString());
            }
            if (dt.Rows.Count == 0)
            {
                return new NotFoundResult();
            }
            return new OkObjectResult(dt);
        }


        [FunctionName("GetBusiness")]
        public static async Task<IActionResult> GetTasks(
            [HttpTrigger(AuthorizationLevel.Anonymous, "get", Route = "business")] HttpRequest req, ILogger log)
        {
            List<BusinessModel> taskList = new List<BusinessModel>();
            try
            {
                using (SqlConnection connection = new SqlConnection(Environment.GetEnvironmentVariable("SqlConnectionString")))
                {
                    connection.Open();
                    var query = @"Select * from tblBusiness b join tblEmployees e on b.BusID=e.busID ";
                    SqlCommand command = new SqlCommand(query, connection);
                    var reader = await command.ExecuteReaderAsync();
                    while (reader.Read())
                    {
                        BusinessModel task = new BusinessModel()
                        {
                            
                            busId = (int)reader["busId"],
                            BusinessName = reader["BusinessName"].ToString(),
                            BusType = reader["BusType"].ToString(),
                            City = reader["City"].ToString(),
                            PhoneNumber =reader["PhoneNumber"].ToString(),
                            Address = reader["Address"].ToString(),
                            Country = reader["Country"].ToString(),
                            OwnershipType = reader["OwnershipType"].ToString(),                           
                            isWomenOwned = reader["isWomenOwned"].ToString(),
                            MBE = reader["MBE"].ToString(),
                            WBE = reader["WBE"].ToString(),
                            MWBE = reader["MWBE"].ToString(),
                            LGBTBE = reader["LGBTBE"].ToString(),
                            VOSB = reader["VOSB"].ToString(),
                            VeteranOwned = reader["VeteranOwned"].ToString(),

                            empId = (int)reader["empId"],
                            Name = reader["Name"].ToString(),
                            Designation = reader["Designation"].ToString(),
                            Gender = reader["Gender"].ToString(),
                            MaritalStatus = reader["MaritalStatus"].ToString(),
                            Race = reader["Race"].ToString(),
                            Ethnicity = reader["Ethnicity"].ToString(),
                            Education = reader["Education"].ToString(),
                            Language = reader["Language"].ToString(),
                            Nationality = reader["Nationality"].ToString(),
                            Disability = reader["Disability"].ToString(),
                            DataSource = reader["DataSource"].ToString(),

                        };
                        taskList.Add(task);
                    }
                }
            }
            catch (Exception e)
            {
                log.LogError(e.ToString());
            }
            if (taskList.Count > 0)
            {
                return new OkObjectResult(taskList);
            }
            else
            {
                return new NotFoundResult();
            }
        }

        [FunctionName("CreateEmployee")]
        public static async Task<IActionResult> Employee(
           [HttpTrigger(AuthorizationLevel.Anonymous, "post", Route = "addemployee")] HttpRequest req, ILogger log)
        {
            string requestBody = await new StreamReader(req.Body).ReadToEndAsync();
            var input = JsonConvert.DeserializeObject<BusinessModel>(requestBody);
            try
            {
                using (SqlConnection connection = new SqlConnection(Environment.GetEnvironmentVariable("SqlConnectionString")))
                {
                    connection.Open();
                    
                    {
                        var query = $"INSERT INTO [tblEmployees] (busID,Name,Designation,Gender,MaritalStatus,Race,Owner,Ethnicity,Education, Language, Nationality,Disability,DataSource) VALUES('{input.busId}', '{input.Name}' , '{input.Designation}',{input.Gender},{input.MaritalStatus},{input.Race},{input.Ethnicity},{input.Education},{input.Language},{input.Nationality},{input.Disability},{input.DataSource})";
                        SqlCommand command = new SqlCommand(query, connection);
                        command.ExecuteNonQuery();

                    }
                }
            }
            catch (Exception e)
            {
                log.LogError(e.ToString());
                return new BadRequestResult();
            }
            return new OkResult();
        }


       
        [FunctionName("DeleteTask")]
        public static IActionResult DeleteTask(
        [HttpTrigger(AuthorizationLevel.Anonymous, "delete", Route = "task/{id}")] HttpRequest req, ILogger log, int id)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(Environment.GetEnvironmentVariable("SqlConnectionString")))
                {
                    connection.Open();
                    var query = @"Delete from TaskList Where Id = @Id";
                    SqlCommand command = new SqlCommand(query, connection);
                    command.Parameters.AddWithValue("@Id", id);
                    command.ExecuteNonQuery();
                }
            }
            catch (Exception e)
            {
                log.LogError(e.ToString());
                return new BadRequestResult();
            }
            return new OkResult();
        }

        // [FunctionName("UpdateTask")]
        //public static async Task<IActionResult> UpdateTask(
        //[HttpTrigger(AuthorizationLevel.Anonymous, "put", Route = "task/{id}")] HttpRequest req, ILogger log, int id)
        //{
        //    string requestBody = await new StreamReader(req.Body).ReadToEndAsync();
        //    var input = JsonConvert.DeserializeObject<UpdateTaskModel>(requestBody);
        //    try
        //    {
        //        using (SqlConnection connection = new SqlConnection(Environment.GetEnvironmentVariable("SqlConnectionString")))
        //        {
        //            connection.Open();
        //            var query = @"Update TaskList Set Description = @Description , IsDone = @IsDone Where Id = @Id";
        //            SqlCommand command = new SqlCommand(query, connection);
        //            command.Parameters.AddWithValue("@Description", input.Description);
        //            command.Parameters.AddWithValue("@IsDone", input.IsDone);
        //            command.Parameters.AddWithValue("@Id", id);
        //            command.ExecuteNonQuery();
        //        }
        //    }
        //    catch (Exception e)
        //    {
        //        log.LogError(e.ToString());
        //    }
        //    return new OkResult();
        //}

        [FunctionName("GetCuratedMaster")]
        public static async Task<IActionResult> GetCuratedMaster(
           [HttpTrigger(AuthorizationLevel.Anonymous, "get", Route = "curatedMaster")] HttpRequest req, ILogger log)
        {
            List<curatedMaster> taskList = new List<curatedMaster>();
            try
            {
                using (SqlConnection connection = new SqlConnection(Environment.GetEnvironmentVariable("SqlConnectionString")))
                {
                    connection.Open();
                    var query = @"Select * from tbl_Hackathon_Data_Curated";
                    SqlCommand command = new SqlCommand(query, connection);
                    var reader = await command.ExecuteReaderAsync();
                    while (reader.Read())
                    {
                        curatedMaster task = new curatedMaster()
                        {
                            dunsNum = (int)reader["dunsNum"],
                            dunsName = reader["dunsName"].ToString(),
                            County = reader["County"].ToString(),
                            City = reader["City"].ToString(),
                            StreetAddress = reader["StreetAddress"].ToString(),
                            State = reader["State"].ToString(),
                            Zip = reader["Zip"].ToString(),
                            phone = reader["phone"].ToString(),
                            ExecutiveContact1 = reader["ExecutiveContact1"].ToString(),
                            ExecutiveContact2 = reader["ExecutiveContact2"].ToString(),
                            isWomanOwned = reader["isWomanOwned"].ToString(),
                            MinorityOwnedDesc = reader["MinorityOwnedDesc"].ToString(),
                            Classification = reader["Classification"].ToString(),
                            Ethnicity = reader["Ethnicity"].ToString(),
                            
                        };
                        taskList.Add(task);
                    }
                }
            }
            catch (Exception e)
            {
                log.LogError(e.ToString());
            }
            if (taskList.Count > 0)
            {
                return new OkObjectResult(taskList);
            }
            else
            {
                return new NotFoundResult();
            }
        }


        [FunctionName("GetCuratedMasterbyId")]
        public static IActionResult GetCuratedMasterbyId(
        [HttpTrigger(AuthorizationLevel.Anonymous, "get", Route = "curatedMaster/{id}")] HttpRequest req, ILogger log, int id)
        {
            DataTable dt = new DataTable();
            try
            {
                using (SqlConnection connection = new SqlConnection(Environment.GetEnvironmentVariable("SqlConnectionString")))
                {
                    connection.Open();
                    var query = @"Select * from tbl_Hackathon_Data_Curated Where dunsNum = @ID";
                    SqlCommand command = new SqlCommand(query, connection);
                    command.Parameters.AddWithValue("@ID", id);
                    SqlDataAdapter da = new SqlDataAdapter(command);
                    da.Fill(dt);
                }
            }
            catch (Exception e)
            {
                log.LogError(e.ToString());
            }
            if (dt.Rows.Count == 0)
            {
                return new NotFoundResult();
            }
            return new OkObjectResult(dt);
        }


    }
}
