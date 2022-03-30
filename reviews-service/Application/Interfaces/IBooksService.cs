using System.Threading.Tasks;
using Domain;

namespace Application.Interfaces
{
    public interface IBooksService
    {
        Task<Book> GetAsync(string isbn, string apiKey, string authorization);
    }
}
