using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Tokens;
using System;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using DotNetEnv;
using Domain;

namespace WebApi.Middlewares
{
    public class JwtUtils
    {
        public static int? ValidateToken(string token)
        {
            if (token == null) 
                return null;

            DotNetEnv.Env.Load();
            var authSecret = Environment.GetEnvironmentVariable("AUTH_SECRET");
            var tokenHandler = new JwtSecurityTokenHandler();

            var key = Encoding.ASCII.GetBytes(authSecret);
            try
            {
                tokenHandler.ValidateToken(token, new TokenValidationParameters
                {
                    ValidateIssuerSigningKey = true,
                    IssuerSigningKey = new SymmetricSecurityKey(key),
                    ValidateIssuer = false,
                    ValidateAudience = false,
                    ClockSkew = TimeSpan.Zero
                }, out SecurityToken validatedToken);

                var jwtToken = (JwtSecurityToken)validatedToken;
                var userId = int.Parse(jwtToken.Claims.First(x => x.Type == "userId").Value);

                return userId;
            }
            catch
            {
                return null;
            }
        }
    }
}