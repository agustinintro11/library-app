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
    public class BooksService : IBooksService
    {
        public async Task<Book> GetAsync(string isbn, string apiKey, string authorization)
        {
            var client = new HttpClient();
            DotNetEnv.Env.Load();
            var booksUrl = Environment.GetEnvironmentVariable("BOOKS_SERVICE_URL");
            try	
            {
                client.DefaultRequestHeaders.Add("api-key", apiKey);
                client.DefaultRequestHeaders.Add("authorization", authorization);
                HttpResponseMessage response = await client.GetAsync(booksUrl + "/books/" + isbn);
                response.EnsureSuccessStatusCode();
                string responseBody = await response.Content.ReadAsStringAsync();

                JObject json = JObject.Parse(responseBody);
                Book book = new Book
                {
                    Id = Int32.Parse(json["id"].ToString()),
                    Isbn = json["isbn"].ToString(),
                    Title = json["title"].ToString(),
                    Authors = json["authors"].ToString(),
                    Year = Int32.Parse(json["year"].ToString()),
                    Quantity = Int32.Parse(json["quantity"].ToString()),
                    OrganizationId = Int32.Parse(json["organizationId"].ToString()),
                    TimesReserved = Int32.Parse(json["timesReserved"].ToString()),
                    Deleted = json["deleted"].ToString() == "true",
                };

                return book;
            }
            catch(HttpRequestException e)
            {
                if (e.StatusCode == HttpStatusCode.NotFound)
                {
                    throw new NotFoundException("Book was not found");
                }
                throw e;
            }
        }
    }
}
