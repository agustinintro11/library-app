using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Threading.Tasks;
using Application.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace Infrastructure.Persistence
{
    public class BaseRepository<T> : IRepository<T> where T : class
    {
        public BaseRepository(DbContext context)
        {
            Context = context;
        }

        protected DbContext Context { get; set; }

        public async Task AddAsync(T entity)
        {
            await Context.Set<T>().AddAsync(entity);
        }

        public void Remove(T entity)
        {
            Context.Set<T>().Remove(entity);
        }

        public async Task<bool> ExistsAsync(Expression<Func<T, bool>> filter)
        {
            IQueryable<T> query = Context.Set<T>();
            return await query.Where(filter).AnyAsync();
        }

        public async Task<T> GetFirstAsync(Expression<Func<T, bool>> filter, string includeProperties = "")
        {
            IQueryable<T> query = Context.Set<T>();
            query = IncludeFromString(query, includeProperties);

            return await query.FirstOrDefaultAsync(filter);
        }

        public async Task<ICollection<T>> GetAllAsync(Expression<Func<T, bool>> filter = null,
            string includeProperties = "")
        {
            IQueryable<T> query = Context.Set<T>();
            query = FilterQuery(query, filter);
            query = IncludeFromString(query, includeProperties);
            return await query.ToListAsync();
        }

        public async Task<ICollection<T>> GetAllWithPaginationAsync(Expression<Func<T, bool>> filter = null,
            string includeProperties = "",
            int page = 1,
            int numItemsPerPage = 10)
        {
            IQueryable<T> query = Context.Set<T>();
            query = FilterQuery(query, filter);
            query = IncludeFromString(query, includeProperties);
            query = query.Skip((page - 1) * numItemsPerPage).Take(numItemsPerPage);
            return await query.ToListAsync();
        }

        public async Task<T> GetAsync(object id)
        {
            return await Context.Set<T>().FindAsync(id);
        }

        public async Task SaveAsync()
        {
            await Context.SaveChangesAsync();
        }

        private static IQueryable<T> FilterQuery(IQueryable<T> query, Expression<Func<T, bool>> filter = null)
        {
            return filter != null ? query.Where(filter) : query;
        }

        private static IQueryable<T> IncludeFromString(IQueryable<T> query, string includeProperties = "")
        {
            var propertiesArray = includeProperties.Split(new[] {','}, StringSplitOptions.RemoveEmptyEntries);

            return propertiesArray.Aggregate(query, (current, includeProperty) => current.Include(includeProperty));
        }
    }
}