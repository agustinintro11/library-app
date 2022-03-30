using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Options;
using System.Linq;
using System.Threading.Tasks;
using System.Collections.Generic;

namespace WebApi.Middlewares
{
    public class JwtMiddleware
    {
        private readonly RequestDelegate _next;

        public JwtMiddleware(RequestDelegate next)
        {
            _next = next;
        }

        public async Task Invoke(HttpContext context)
        {
            if (context.Request.Headers["Authorization"].FirstOrDefault() != null)
            {
                var token = context.Request.Headers["Authorization"].FirstOrDefault()?.Split(" ").Last();
                var userId = JwtUtils.ValidateToken(token);
                if (userId != null)
                {
                    context.Items["User"] = new Dictionary<string, string>();
                    ((IDictionary<string,string>)context.Items["User"]).Add("UserId", "" + userId.Value);
                    ((IDictionary<string,string>)context.Items["User"]).Add("IsAdmin", "false");
                }
            }
            await _next(context);
        }
    }
}
