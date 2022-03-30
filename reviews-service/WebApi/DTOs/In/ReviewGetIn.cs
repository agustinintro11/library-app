using System;

namespace DTOs.In
{
    public class ReviewGetIn
    {
        public int Page { get; set; }
        public int Limit { get; set; }
        public string Isbn { get; set; }

        public bool HasErrors()
        {
            return Validate() != null;
        }

        public void SetDefaultValues()
        {
            Page = Math.Max(1, Page);
            Limit = Math.Max(1, Math.Min(200, Limit));
        }

        public Tuple<string, string> Validate()
        {
            if (Isbn == null || Isbn.Length == 0)
            {
                return Tuple.Create("ISBN", "ISBN cannot be empty");
            }

            return null;
        }
    }
}
