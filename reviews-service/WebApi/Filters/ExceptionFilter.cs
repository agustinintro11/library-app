using System;
using System.Collections.Generic;
using Application.Exceptions;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Filters;
using Newtonsoft.Json;

namespace WebApi.Filters
{
    public class ExceptionFilter : IExceptionFilter
    {
        public void OnException(ExceptionContext context)
        {
            try
            {
                throw context.Exception;
            }
            catch (NotAuthenticatedException)
            {
                context.Result = ResponseForSingleError(401, "Unauthorized");
            }
            catch (NotAuthorizedException exception)
            {
                context.Result = ResponseForSingleError(403, exception.Message);
            }
            catch (ClientSideException exception)
            {
                context.Result = ResponseForSingleError(400, exception.Message);
            }
            catch (NotFoundException exception)
            {
                context.Result = ResponseForSingleError(404, exception.Message);
            }
            catch (NotUniqueException exception)
            {
                context.Result = ResponseForSingleError(409, exception.Message);
            }
            catch (Exception exception)
            {
                context.Result = ResponseForException(500, exception);
            }
        }

        private static ContentResult ResponseForException(int httpStatus, Exception exception)
        {
            return ResponseForDictionary(httpStatus, AddOuterKeyToDictionary("error", exception.Message));
        }

        private static ContentResult ResponseForSingleError(int httpStatus, string error)
        {
            return ResponseForDictionary(httpStatus, AddOuterKeyToDictionary("error", error));
        }

        private static ContentResult ResponseForDictionary<T, V>(int httpStatus, IDictionary<T, V> dictionary)
        {
            var content = DictionaryToJson(dictionary);
            return new ContentResult {StatusCode = httpStatus, Content = content};
        }

        private static IDictionary<string, string> AddOuterKeyToDictionary(string key, string error)
        {
            var response = new Dictionary<string, string> {[key] = error};

            return response;
        }

        private static string DictionaryToJson<T, V>(IDictionary<T, V> dictionary)
        {
            return JsonConvert.SerializeObject(dictionary, Formatting.Indented);
        }
    }
}
