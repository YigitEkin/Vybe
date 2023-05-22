export const fetchData = async (url: string, type: string, body?: any) => {
  const BASE_URL = 'http://35.228.161.177:8080';
  console.log(localStorage.getItem('authkey'));
  let token = '';
  if (localStorage.getItem('authkey')) {
    token = localStorage.getItem('authkey').replaceAll("^\"|\"$", "")
  }
  console.log(token);
  const response = await fetch(`${BASE_URL}${url}`, {
    method: type,
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify(body)
  });
  if (!response.ok) {
    const message = `An error has occured: ${response.status}`;
    throw new Error(message);
  }
  if (type === 'POST') {
    const data = await response.text();
    console.log(data);
    return data;
  }
  else {
    const data = await response.json();
    return data;
  }
}