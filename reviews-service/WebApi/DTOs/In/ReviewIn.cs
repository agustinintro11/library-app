using System;

namespace DTOs.In
{
    public class ReviewIn
    {
        public int Rating { get; set; }
        public string Description { get; set; }
        public string Isbn { get; set; }

        public bool HasErrors()
        {
            return Validate() != null;
        }

        public Tuple<string, string> Validate()
        {
            if (Rating < 1 || Rating > 5)
            {
                return Tuple.Create("Rating", "Rating should be between 1 and 5");
            }

            if (Description == null || Description.Length == 0)
            {
                return Tuple.Create("Description", "Description cannot be empty");
            }
        
            if (Isbn == null || Isbn.Length == 0)
            {
                return Tuple.Create("ISBN", "ISBN cannot be empty");
            }

            return null;
        }
    }
}
