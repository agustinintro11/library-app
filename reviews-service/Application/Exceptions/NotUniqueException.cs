using System;

namespace Application.Exceptions
{
    public class NotUniqueException : Exception
    {
        public NotUniqueException(string message) : base(message)
        { }
    }
}
