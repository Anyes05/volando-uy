// Carousel
const carousel = document.querySelector(".carousel");
const prevBtn = document.querySelector(".prev");
const nextBtn = document.querySelector(".next");

if (carousel && prevBtn && nextBtn) {
  nextBtn.addEventListener("click", () => {
    carousel.scrollBy({ left: 220, behavior: "smooth" });
  });

  prevBtn.addEventListener("click", () => {
    carousel.scrollBy({ left: -220, behavior: "smooth" });
  });
}
