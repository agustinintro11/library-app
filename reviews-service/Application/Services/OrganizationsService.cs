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
    public class OrganizationsService : IOrganizationsService
    {
        public async Task<Organization> GetAsync(string apiKey)
        {
            var client = new HttpClient();
            DotNetEnv.Env.Load();
            var organizationUrl = Environment.GetEnvironmentVariable("ORGANIZATIONS_SERVICE_URL");
            try	
            {
                client.DefaultRequestHeaders.Add("api-key", apiKey);
                HttpResponseMessage response = await client.GetAsync(organizationUrl + "/organizations");
                response.EnsureSuccessStatusCode();
                string responseBody = await response.Content.ReadAsStringAsync();

                JObject json = JObject.Parse(responseBody);
                Organization org = new Organization
                {
                    Id = Int32.Parse(json["id"].ToString()),
                    Name = json["name"].ToString(),
                    ApiKey = json["apiKey"].ToString(),
                };

                return org;
            }
            catch(HttpRequestException e)
            {
                if (e.StatusCode == HttpStatusCode.NotFound)
                {
                    throw new NotFoundException("Organization was not found");
                }
                throw e;
            }
        }
    }
}
