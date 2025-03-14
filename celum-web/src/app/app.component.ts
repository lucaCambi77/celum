import {Component} from '@angular/core';
import {RouterModule} from '@angular/router';
import {NavBarComponent} from './nav-bar/nav-bar.component';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    standalone: true,
    imports: [
        RouterModule,
        NavBarComponent],
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    title = 'celum-web';
}
