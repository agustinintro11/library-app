using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using System.Threading.Tasks;

namespace Application.Interfaces
{
    public interface IRepository<T> where T : class
    {
        Task AddAsync(T entity);
        void Remove(T entity);
        Task<bool> ExistsAsync(Expression<Func<T, bool>> filter);

        Task<ICollection<T>> GetAllAsync(Expression<Func<T, bool>> filter = null, string includeProperties = "");

        Task<ICollection<T>> GetAllWithPaginationAsync(Expression<Func<T, bool>> filter = null,
            string includeProperties = "",
            int page = 1,
            int resultsPerPge = 10);

        Task<T> GetAsync(object id);
        Task<T> GetFirstAsync(Expression<Func<T, bool>> filter, string includeProperties = "");

        Task SaveAsync();
    }
}
