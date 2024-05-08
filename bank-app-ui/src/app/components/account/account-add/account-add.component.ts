import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Account } from 'src/app/model/account.model';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';

@Component({
  selector: 'app-account-add',
  templateUrl: './account-add.component.html',
  styleUrls: ['./account-add.component.css']
})
export class AccountAddComponent implements OnInit {
  model = new Account();

  constructor(private dashboardService: DashboardService) { }
  
  ngOnInit(): void {
  }

  save(form: NgForm) {
    this.dashboardService.saveAccount(this.model).subscribe(response => {
      this.model = <any> response.body;
      form.resetForm();
    });
  }

}
