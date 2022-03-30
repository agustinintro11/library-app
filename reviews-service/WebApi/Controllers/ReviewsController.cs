using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Domain;
using Application.Interfaces;
using DTOs.In;
using DTOs.Out;
using WebApi.Attributes;

namespace _211150_211064_186499_reviews_service.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class ReviewsController : ControllerBase
    {
        private readonly ILogger<ReviewsController> Logger;
        private readonly IReviewsService ReviewService;

        public ReviewsController(ILogger<ReviewsController> logger, IReviewsService reviewService)
        {
            Logger = logger;
            ReviewService = reviewService;
        }

        [HttpGet]
        [AuthorizeAttribute]
        public async Task<IActionResult> GetAsync([FromQuery] ReviewGetIn reviewGetIn)
        {
            if (reviewGetIn.HasErrors())
            {
                var validation = reviewGetIn.Validate();
                return BadRequest(new BadRequestOut("query", validation.Item1, validation.Item2));
            }
            reviewGetIn.SetDefaultValues();

            string apiKey = Request.Headers["api-key"];
            string authorization = Request.Headers["authorization"];

            return Ok(await ReviewService.GetAsync(reviewGetIn.Isbn, reviewGetIn.Page, reviewGetIn.Limit, apiKey, authorization));
        }

        [HttpPost]
        [AuthorizeAttribute]
        public async Task<IActionResult> PostAsync(ReviewIn reviewIn)
        {
            if (reviewIn.HasErrors())
            {
                var validation = reviewIn.Validate();
                return BadRequest(new BadRequestOut("body", validation.Item1, validation.Item2));
            }

            var review = new Review()
            {
                Rating = reviewIn.Rating,
                Description = reviewIn.Description,
                Isbn = reviewIn.Isbn,
            };

            string apiKey = Request.Headers["api-key"];
            string authorization = Request.Headers["authorization"];
            
            return Ok(await ReviewService.CreateAsync(review, apiKey, authorization));
        }

        [HttpDelete("{id}")]
        [AuthorizeAttribute]
        public async Task<IActionResult> DeleteAsync([FromRoute] int id)
        {
            int userId = Int32.Parse(((Dictionary<string, string>)HttpContext.Items["User"])["UserId"]);
            string apiKey = Request.Headers["api-key"];

            await ReviewService.DeleteAsync(id, userId, apiKey);

            return NoContent();
        }
    }
}
