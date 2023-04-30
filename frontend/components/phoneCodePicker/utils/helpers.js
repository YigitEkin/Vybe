export function formatPhoneNumber(phoneNumberString) {
  const cleaned = ("" + phoneNumberString).replace(/\D/g, "");
  const match = cleaned.match(/^(\d{3})(\d{3})(\d{4})$/);
  if (match) {
    return "(" + match[1] + ") " + match[2] + "-" + match[3];
  }
  return null;
}

//remove () and - and space from phone number
export function deFormatPhoneNumber(phoneNumberString) {
  const cleaned = phoneNumberString
    .replace("(", "")
    .replace(")", "")
    .replace("-", "")
    .replace(" ", "");
  console.log("cleaned", cleaned);
  return cleaned;
}
