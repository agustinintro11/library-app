using System;
using Domain;
using System.Threading.Tasks;
using Application.Interfaces;
using DotNetEnv;
using System.Net.Http;
using System.Net;
using Newtonsoft.Json.Linq;
using Application.Exceptions;

namespace Application.Services
{
    public class UsersService : IUsersService
    {
        public async Task<User> GetAsync(string apiKey, string authorization)
        {
            var client = new HttpClient();
            DotNetEnv.Env.Load();
            var usersUrl = Environment.GetEnvironmentVariable("USERS_SERVICE_URL");
            try	
            {
                client.DefaultRequestHeaders.Add("api-key", apiKey);
                client.DefaultRequestHeaders.Add("authorization", authorization);
                HttpResponseMessage response = await client.GetAsync(usersUrl + "/users/me");
                response.EnsureSuccessStatusCode();
                string responseBody = await response.Content.ReadAsStringAsync();

                JObject json = JObject.Parse(responseBody);
                User user = new User
                {
                    Id = Int32.Parse(json["id"].ToString()),
                    Name = json["name"].ToString(),
                    Email = json["email"].ToString(),
                    IsAdmin = json["email"].ToString() == "true",
                };

                return user;
            }
            catch(HttpRequestException e)
            {
                if (e.StatusCode == HttpStatusCode.NotFound)
                {
                    throw new NotFoundException("User was not found");
                }
                throw e;
            }
        }
    }
}
