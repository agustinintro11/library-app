using System.Threading.Tasks;
using Domain;

namespace Application.Interfaces
{
    public interface IOrganizationsService
    {
        Task<Organization> GetAsync(string apiKey);
    }
}
