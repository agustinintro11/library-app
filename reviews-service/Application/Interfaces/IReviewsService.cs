using System.Threading.Tasks;
using Domain;
using System.Collections.Generic;

namespace Application.Interfaces
{
    public interface IReviewsService
    {
        Task<Review> CreateAsync(Review review, string apiKey, string authorization);
        Task<ICollection<Review>> GetAsync(string isbn, int page, int limit, string apiKey, string authorization);
        Task DeleteAsync(int id, int userId, string apiKey);
    }
}
