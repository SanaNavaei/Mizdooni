import { useState, useEffect } from 'react';

function Reserve({ maxSeatNumber, availableTimes }) {
  const [selectedDate, setSelectedDate] = useState('');
  const [filteredAvailableTimes, setFilteredAvailableTimes] = useState([]);

  useEffect(() => {
    setFilteredAvailableTimes(availableTimes);
  }, [availableTimes]);

  const seatOptions = [];
  for (let i = 1; i <= maxSeatNumber; i++) {
    seatOptions.push(<option key={i} value={i}>{i}</option>);
  }

  const handleDateChange = (e) => {
    this.selectedDate = e.target.value;
    setSelectedDate(selectedDate);

    const filteredTimes = availableTimes.filter(time => {
      return time.date === selectedDate;
    });
    setFilteredAvailableTimes(filteredTimes);
  };

  return (
    <section className="col-lg">
      <h2 className="fw-semibold fs-5 mb-4">Reserve Table</h2>
      <form id="reserve-form">
        <div className="ps-2 ps-sm-0">
          <label htmlFor="people">For</label>
          <select className="form-select mx-1" name="people" id="people">{seatOptions}</select>
          <span>people, </span>
          <br className="d-sm-none" />
          <label className="pt-3 pt-sm-0" htmlFor="date"> on date </label>
          <input className="form-control mx-1" type="date" name="date" id="date" required onChange={handleDateChange} />
        </div>

        <p className="mt-3 mb-2">Available Times</p>
        <div className="row row-cols-4 row-cols-md-5 row-cols-lg-4 g-2">
          {filteredAvailableTimes.map((time, index) => (
            <div key={index} className="col">
              <input className="btn-check" type="radio" name="time" value={time.time} id={`time${index + 1}`} required />
              <label className="available-time d-block btn" htmlFor={`time${index + 1}`}>{time.time}</label>
            </div>
          ))}
        </div>

        <p id="one-hour-text" className="miz-text-red my-2">
          You will reserve this table only for
          <span className="text-decoration-underline"> one </span>
          hour, for more time please contact the restaurant.
        </p>
        <button type="submit" className="miz-button w-100">Complete the Reservation</button>
      </form>
    </section>
  );
}

export default Reserve;
