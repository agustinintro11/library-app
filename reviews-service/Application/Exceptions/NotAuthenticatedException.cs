using System;

namespace Application.Exceptions
{
    public class NotAuthenticatedException : Exception
    {
        public NotAuthenticatedException(string message) : base(message)
        { }
    }
}
