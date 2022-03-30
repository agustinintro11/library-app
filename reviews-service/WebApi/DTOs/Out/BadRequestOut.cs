using System.Collections.Generic;

namespace DTOs.Out
{
    public class BadRequestOut
    {
        public int StatusCode { get; set; }
        public string Error { get; set; }
        public string Message { get; set; }
        public Dictionary<string, dynamic> Validation { get; set; }

        public BadRequestOut(string segment, string field, string error)
        {
            StatusCode = 400;
            Error = "Bad Request";
            Message = "Validation failed";

            Validation = new Dictionary<string, dynamic>();
            Validation[segment] = new Dictionary<string, dynamic>();
            Validation[segment]["source"] = segment;
            Validation[segment]["keys"] = new List<string>{field};
            Validation[segment]["message"] = error;
        }
    }
}
