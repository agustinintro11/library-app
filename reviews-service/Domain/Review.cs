using System;

namespace Domain
{
    public class Review : AuditableBaseEntity
    {
        public int Rating { get; set; }
        public string Description { get; set; }
        public int OrganizationId { get; set; }
        public int BookId { get; set; }
        public int UserId { get; set; }
        public string Isbn { get; set; }
        public string Username { get; set; }
    }
}
