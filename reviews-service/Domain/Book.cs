using System;

namespace Domain
{
    public class Book
    {
        public int Id { get; set; }
        public string Isbn { get; set; }
        public string Title { get; set; }
        public string Authors { get; set; }
        public int Year { get; set; }
        public int Quantity { get; set; }
        public int OrganizationId { get; set; }
        public int TimesReserved { get; set; }
        public bool Deleted { get; set; }
    }
}
