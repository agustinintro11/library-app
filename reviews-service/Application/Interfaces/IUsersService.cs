using System.Threading.Tasks;
using Domain;

namespace Application.Interfaces
{
    public interface IUsersService
    {
        Task<User> GetAsync(string apiKey, string authorization);
    }
}
