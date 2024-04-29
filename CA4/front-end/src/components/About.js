import banner from '../assets/images/home/banner.png';

function About () {
    return (
        <article className="container pt-5">
        <div className="row row-cols-1 row-cols-md-2 align-items-center">
          <div className="col">
            <img className="banner-img w-100 object-fit-cover" src={banner} alt="Restaurant table" />
          </div>
          <div className="col">
            <h2 className="miz-text-red fs-4 fw-normal text-center text-md-start">About Mizdooni</h2>
            <div className="text-justify fw-light pt-3 fs-6">
              <p>Are you tired of waiting in long lines at restaurants or struggling to find a table at your favorite eatery?</p>
              <p>Look no further than Mizdooni - the ultimate solution for all your dining reservation needs.</p>
              <p>Mizdooni is a user-friendly website where you can reserve a table at any restaurant, from anywhere, at a specific time.
                Whether you're craving sushi, Italian, or a quick bite to eat, Mizdooni has you covered.</p>
              <p>With a simple search feature, you can easily find a restaurant based on cuisine or location.</p>
              <p><span className="miz-text-red">The best part?</span> There are no fees for making a reservation through Mizdooni.
                Say goodbye to the hassle of calling multiple restaurants or showing up only to find there's a long wait.
                With Mizdooni, you can relax knowing that your table is secured and waiting for you.</p>
              <p>Don't let dining out be a stressful experience.
                Visit Mizdooni today and start enjoying your favorite meals without the headache of making reservations.
                Reserve your table with ease and dine with peace of mind.</p>
            </div>
          </div>
        </div>
      </article>
    )
}

export default About;
