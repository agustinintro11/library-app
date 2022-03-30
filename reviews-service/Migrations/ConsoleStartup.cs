using Microsoft.Extensions.DependencyInjection;
using Microsoft.EntityFrameworkCore;
using System.Reflection;
using System;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using DotNetEnv;
using Infrastructure.Persistence;

public class ConsoleStartup
{
    public ConsoleStartup()
    {
        var builder = new ConfigurationBuilder();

        Configuration = builder.Build();
    }

    public IConfiguration Configuration { get; }
    public void ConfigureServices(IServiceCollection services)
    {
        DotNetEnv.Env.Load("../.env");
        var connectionString = Environment.GetEnvironmentVariable("CONNECTION_STRING");

        services.AddDbContext<ReviewsServiceContext>(options =>
        {
            options.UseNpgsql(connectionString);
        });
    }

    public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
    { }
}
