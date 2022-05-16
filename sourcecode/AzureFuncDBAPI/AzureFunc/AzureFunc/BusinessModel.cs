using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AzureFunc
{
    internal class BusinessModel
    {
        public int busId { get; set; }
        public string BusinessName { get; set; }
        public string BusType { get; set; }
        public string Address { get; set; }
        public string City { get; set; }
        public string PhoneNumber { get; set; }
        

        public string Country { get; set; }
        public string OwnershipType { get; set; }
        public string isWomenOwned { get; set; }

        public string MBE { get; set; }
        public string WBE { get; set; }
        public string MWBE { get; set; }
        public string LGBTBE { get; set; }
        public string VOSB { get; set; }
        public string VeteranOwned { get; set; }



        public int empId { get; set; }
        public string Name { get; set; }
        public string Designation { get; set; }

        public string Gender { get; set; }
        public string MaritalStatus { get; set; }
        public string Race { get; set; }
        public string Ethnicity { get; set; }
        public string Education { get; set; }
        public string Language { get; set; }
        public string Nationality{ get; set; }
        public string Disability { get; set; }
        public string DataSource { get; set; }

    }

    internal class curatedMaster
    {
        public int dunsNum { get; set; }
        public string dunsName { get; set; }
        public string County { get; set; }
        public string StreetAddress { get; set; }
        public string City { get; set; }
        public string State { get; set; }
        public string Zip { get; set; }
        public string phone { get; set; }
        public string ExecutiveContact1 { get; set; }
        public string ExecutiveContact2 { get; set; }
        public string isWomanOwned { get; set; }
        public string MinorityOwnedDesc { get; set; }
        public string Classification { get; set; }
        public string Ethnicity { get; set; }


    }
}
