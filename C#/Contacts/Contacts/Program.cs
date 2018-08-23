// Tyler Teixeira

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Contacts
{
    public class ContactInfo
    {
        public ContactInfo next;

        public string firstName, lastName;
        public string address, city, state;

        // constructor for contacts.
        public ContactInfo(string first, string last, string addr, string cit, string st)
        {
            firstName = first;
            lastName = last;
            address = addr;
            city = cit;
            state = st;

            next = null;
        }

        // constructor for header.
        public ContactInfo()
        {
            next = null;
        }

        // function to add to linked list (recursive).
        public void Add(ContactInfo newContact)
        {
            if (this.next == null)
                this.next = newContact;
            else
                this.next.Add(newContact);
        }
    }

    public class Program
    {
        static void Main(string[] args)
        {
            // header for linked list.
            ContactInfo contacts = new ContactInfo();

            // ask user to input contacts until he/she enters 'quit'.
            while (true)
            {
                contacts.Add(AskForContactInfo());

                Console.WriteLine("Type 'quit' to quit. Otherwise, type anything to continue adding contacts. ");
                if (Console.ReadLine() == "quit")
                    break;
            }

            DisplayContacts(contacts);

            Console.WriteLine("\nPress enter to close window");
            Console.ReadLine();
        }

        // fill a Contactinfo object with contact info.
        public static ContactInfo AskForContactInfo()
        {
            string first, last, addr, cit, st;

            Console.WriteLine("\nPlease enter contact first name: ");
            first = Console.ReadLine();
            Console.WriteLine("\nPlease enter contact last name: ");
            last = Console.ReadLine();
            Console.WriteLine("\nPlease enter contact address: ");
            addr = Console.ReadLine();
            Console.WriteLine("\nPlease enter contact city: ");
            cit = Console.ReadLine();
            Console.WriteLine("\nPlease enter contact state: ");
            st = Console.ReadLine();

            ContactInfo contact = new ContactInfo(first, last, addr, cit, st);
            return contact;
        }

        // display all entered contacts after user types 'quit (recursive).
        public static void DisplayContacts(ContactInfo contacts)
        {
            if (contacts.next != null)
            {
                Console.Write("|" + contacts.next.firstName + "|" + contacts.next.lastName + "|" + contacts.next.address + "|" + contacts.next.city + "|" + contacts.next.state + "|\n");
                DisplayContacts(contacts.next);
            }
        }
    }
}
