import { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import CompleteReserveModal from './CompleteReserveModal';

const getCurrentDate = () => {
  const date = new Date();
  const today = new Date(date.getTime() - (date.getTimezoneOffset() * 60000));
  return today.toISOString().slice(0, 10);
};

function Reserve({ maxSeatsNumber, address, id: restaurantId }) {
  const [selectedPeople, setSelectedPeople] = useState(0);
  const [selectedDate, setSelectedDate] = useState('');
  const [selectedTime, setSelectedTime] = useState('');
  const [isButtonDisabled, setIsButtonDisabled] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [availableTimes, setAvailableTimes] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [tableNumber, setTableNumber] = useState(0);

  useEffect(() => {
    setTableNumber(0);
    setIsButtonDisabled(!(selectedPeople && selectedDate && selectedTime));
  }, [selectedPeople, selectedDate, selectedTime]);

  useEffect(() => {
    if (!selectedPeople || !selectedDate) {
      setAvailableTimes([]);
      return;
    }
    fetchTimes()
  }, [selectedPeople, selectedDate]);

  const fetchTimes = () => {
    const query = new URLSearchParams({ people: selectedPeople, date: selectedDate })
    fetch(`/api/reserves/${restaurantId}/available?` + query, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to fetch available times');
        }
        return response.json();
      })
      .then(data => {
        setAvailableTimes(data.data);
      })
      .catch(error => {
        setAvailableTimes([]);
        console.error('Error fetching available times:', error);
      });
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    setShowModal(false);
    fetch(`/api/reserves/${restaurantId}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        people: selectedPeople,
        datetime: `${selectedDate} ${selectedTime}`
      }),
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to reserve table');
        }
        return response.json();
      })
      .then(data => {
        console.debug('Success:', data);
        setTableNumber(data.data.table.tableNumber);
        setShowModal(true);
        fetchTimes();
      })
      .catch(error => {
        toast.error(error.message, {
          position: 'top-right',
          autoClose: 3000,
        });
      });
  };

  const handlePeopleChange = (e) => {
    if (!e.target.value) {
      setSelectedPeople(0);
      return;
    }
    setSelectedPeople(e.target.value);
  };

  const handleDateChange = (e) => {
    if (!e.target.value) {
      setSelectedDate('');
      return;
    }
    const selected = new Date(e.target.value);
    const today = new Date();
    const date = new Date(today.getFullYear(), today.getMonth() + 1, today.getDate());
    const oneMonthFromNow = new Date(date.getTime() - (date.getTimezoneOffset() * 60000))

    if (selected > oneMonthFromNow) {
      const maxDate = oneMonthFromNow.toISOString().slice(0, 10);
      e.target.value = '';
      setErrorMessage(`The maximum possible date for reservation is ${maxDate}`);
      setSelectedDate('');
    } else {
      setErrorMessage('');
      setSelectedDate(e.target.value);
    }
  };

  const handleTimeChange = (e) => {
    setSelectedTime(e.target.value);
  };

  return (
    <section className="col-lg">
      <h2 className="fw-semibold fs-5 mb-4">Reserve Table</h2>
      <form id="reserve-form" onSubmit={handleSubmit}>
        <div className="ps-2 ps-sm-0">
          <label htmlFor="people">For</label>
          <select className="form-select mx-1" name="people" id="people" onChange={handlePeopleChange} value={selectedPeople}>
            <option key="empty" value=""></option>
            {[...Array(maxSeatsNumber)].map((_, i) => (
              <option key={i + 1} value={i + 1}>{i + 1}</option>
            ))}
          </select>
          <span>people, </span>
          <br className="d-sm-none" />
          <label className="pt-3 pt-sm-0" htmlFor="date"> on date </label>
          <input className="form-control mx-1" type="date" name="date" id="date" min={getCurrentDate()} required onChange={handleDateChange} />
        </div>

        {errorMessage && (
          <p className="miz-text-red my-4">{errorMessage}</p>
        )}

        {(!errorMessage && selectedPeople > 0 && selectedDate) ? (
          <>
            {availableTimes.length > 0 ? (
              <>
                <p className="mt-3 mb-2">Available Times</p>
                <div className="row row-cols-4 row-cols-md-5 row-cols-lg-4 g-2">
                  {availableTimes.map((time, index) => (
                    <div key={index} className="col">
                      <input className="btn-check" type="radio" name="time" value={time} id={`time${index + 1}`} required onChange={handleTimeChange} />
                      <label className="available-time d-block btn" htmlFor={`time${index + 1}`}>{time}</label>
                    </div>
                  ))}
                </div>
                <p id="one-hour-text" className="miz-text-red my-2">
                  You will reserve this table only for
                  <span className="text-decoration-underline"> one </span>
                  hour, for more time please contact the restaurant.
                </p>
              </>
            ) : (
              <p className="my-4">No table is available on this date.</p>
            )}
          </>
        ) : (
          <div>
            <p className="mt-3 mb-2">Available Times</p>
            <p className="miz-text-red my-2">Select the number of people and date.</p>
          </div>
        )}
        <button type="submit" className="miz-button disabled-button w-100" data-bs-toggle="modal" data-bs-target="#modal-complete-reservation" disabled={isButtonDisabled}>Complete the Reservation</button>
      </form>

      {showModal && (
        <CompleteReserveModal country={address.country} city={address.city} street={address.street} tableNumber={tableNumber} time={selectedTime} />
      )}
    </section>
  );
}

export default Reserve;
