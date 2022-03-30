using System.Reflection;
using Application.Interfaces;
using Application.Services;
using AutoMapper;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.EntityFrameworkCore;
using Domain;
using Infrastructure.Persistence;
using DotNetEnv;

namespace Infrastructure
{
    public static class ServiceExtensions
    {
        public static void AddInfrastructureLayer(this IServiceCollection services)
        {
            services.AddScoped<DbContext, ReviewsServiceContext>();
            services.AddScoped<IRepository<Review>, BaseRepository<Review>>();
        }
    }
}
