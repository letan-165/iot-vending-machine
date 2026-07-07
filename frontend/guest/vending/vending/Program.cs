using System;
using System.Collections.Generic;
using System.Windows.Forms;
using vending.model;
using vending.page;

namespace VendingMachineApp
{
    internal static class Program
    {
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            Application.Run(new ProductPage());
        }
    }
}
