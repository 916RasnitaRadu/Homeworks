

// Get the thumbnails and full image container elements
const thumbnails = document.getElementById('thumbnails');
const fullImageContainer = document.getElementById('fullsize-container');
// Get the full image element within the container
const fullImage = document.getElementById('fullsize');

// Add event listener to the gallery to listen for mouseover events
thumbnails.addEventListener('mouseover', (event) => {

  if (event.target.tagName === 'IMG') {
    fullImage.src = event.target.getAttribute('data-full-size');
    fullImageContainer.style.display = 'block';
  }

});

// Add event listener to the gallery to listen for mouseout events
thumbnails.addEventListener('mouseout', (event) => {
  if (event.target.tagName === 'IMG') {
    fullImageContainer.style.display = 'none';
  }
});
