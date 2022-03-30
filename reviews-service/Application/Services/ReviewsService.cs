using System;
using Domain;
using System.Threading.Tasks;
using Application.Interfaces;
using DotNetEnv;
using System.Collections.Generic;
using Application.Exceptions;

namespace Application.Services
{
    public class ReviewsService : IReviewsService
    {
        IRepository<Review> ReviewRepository;
        IOrganizationsService OrganizationsService;
        IUsersService UsersService;
        IBooksService BooksService;
    
        public ReviewsService(
            IRepository<Review> reviewRepository,
            IOrganizationsService organizationsService,
            IBooksService booksService,
            IUsersService usersService
        )
        {
            ReviewRepository = reviewRepository;
            OrganizationsService = organizationsService;
            BooksService = booksService;
            UsersService = usersService;
        }

        public async Task<Review> CreateAsync(Review review, string apiKey, string authorization)
        {
            var book = await BooksService.GetAsync(review.Isbn, apiKey, authorization);
            var user = await UsersService.GetAsync(apiKey, authorization);
            var organization = await OrganizationsService.GetAsync(apiKey);

            review.BookId = book.Id;
            review.OrganizationId = organization.Id;
            review.UserId = user.Id;
            review.Username = user.Name;

            await ReviewRepository.AddAsync(review);
            await ReviewRepository.SaveAsync();

            return review;
        }

        public async Task<ICollection<Review>> GetAsync(string isbn, int page, int limit, string apiKey, string authorization)
        {
            var organization = await OrganizationsService.GetAsync(apiKey);
            var book = await BooksService.GetAsync(isbn, apiKey, authorization);

            return await ReviewRepository.GetAllWithPaginationAsync(x => x.BookId == book.Id, "", page, limit);
        }

        public async Task DeleteAsync(int id, int userId, string apiKey)
        {
            var organization = await OrganizationsService.GetAsync(apiKey);
            var toDelete = await ReviewRepository.GetFirstAsync(x => x.Id == id);

            if (toDelete == null || organization.Id != toDelete.OrganizationId)
            {
                throw new NotFoundException("Review not found");
            }
            if (toDelete.UserId != userId)
            {
                throw new NotAuthorizedException("Cannot delete another's person review");
            }
            if ((DateTime.Now - toDelete.Created) > TimeSpan.FromHours(24))
            {
                throw new ClientSideException("Cannot delete review after 24 hours have passed");
            }
            
            ReviewRepository.Remove(toDelete);
            await ReviewRepository.SaveAsync();
        }  
    }
}
