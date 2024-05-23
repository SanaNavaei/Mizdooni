export const getCurrentDate = () => {
  const date = new Date();
  const today = new Date(date.getTime() - (date.getTimezoneOffset() * 60000));
  return today.toISOString().slice(0, 10);
};
