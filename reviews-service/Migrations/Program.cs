using System;
using System.IO;
using Infrastructure.Persistence;
using Microsoft.AspNetCore.Hosting;
using Microsoft.EntityFrameworkCore;

namespace Migrations
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Applying migrations");

            var webHost = new WebHostBuilder()
                .UseContentRoot(Directory.GetCurrentDirectory())
                .UseStartup<ConsoleStartup>()
                .Build();

            using (var context = (ReviewsServiceContext) webHost.Services.GetService(typeof(ReviewsServiceContext)))
            {
                context.Database.Migrate();
            }
            Console.WriteLine("Done");
        }
    }
}
